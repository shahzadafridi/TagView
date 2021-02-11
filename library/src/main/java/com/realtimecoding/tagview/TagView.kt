package com.realtimecoding.tagview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.*

class TagView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    /**
     * tag list
     */
    private var mTags: MutableList<Tag> = ArrayList()

    /**
     * System Service
     */
    private var mInflater: LayoutInflater? = null
    private var mViewTreeObserber: ViewTreeObserver? = null

    /**
     * listeners
     */
    private var mClickListener: OnTagClickListener? = null
    private var mDeleteListener: OnTagDeleteListener? = null
    private var mTagLongClickListener: OnTagLongClickListener? = null

    /**
     * view size param
     */
    private var mWidth = 0

    /**
     * layout initialize flag
     */
    private var mInitialized = false

    /**
     * custom layout param
     */
    private var lineMargin = 0
    private var tagMargin = 0
    private var textPaddingLeft = 0
    private var textPaddingRight = 0
    private var textPaddingTop = 0
    private var textPaddingBottom = 0

    private var ctx: Context

    init {
        ctx = context!!
        initialize(ctx, attrs)
    }

    /**
     * initalize instance
     *
     * @param ctx
     * @param attrs
     * @param defStyle
     */
    private fun initialize(ctx: Context, attrs: AttributeSet?) {
        mInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mViewTreeObserber = viewTreeObserver
        mViewTreeObserber!!.addOnGlobalLayoutListener(OnGlobalLayoutListener {
            if (!mInitialized) {
                mInitialized = true
                drawTags()
            }
        })

        // get AttributeSet
        val typeArray = ctx.obtainStyledAttributes(attrs, R.styleable.TagView)
        lineMargin = typeArray.getDimension(
            R.styleable.TagView_lineMargin,
            Utils.dipToPx(this.context, Constants.DEFAULT_LINE_MARGIN).toFloat()
        )
            .toInt()
        tagMargin = typeArray.getDimension(
            R.styleable.TagView_tagMargin,
            Utils.dipToPx(this.context, Constants.DEFAULT_TAG_MARGIN).toFloat()
        )
            .toInt()
        textPaddingLeft = typeArray.getDimension(
            R.styleable.TagView_textPaddingLeft,
            Utils.dipToPx(this.context, Constants.DEFAULT_TAG_TEXT_PADDING_LEFT).toFloat()
        )
            .toInt()
        textPaddingRight = typeArray.getDimension(
            R.styleable.TagView_textPaddingRight,
            Utils.dipToPx(this.context, Constants.DEFAULT_TAG_TEXT_PADDING_RIGHT).toFloat()
        )
            .toInt()
        textPaddingTop = typeArray.getDimension(
            R.styleable.TagView_textPaddingTop,
            Utils.dipToPx(this.context, Constants.DEFAULT_TAG_TEXT_PADDING_TOP).toFloat()
        )
            .toInt()
        textPaddingBottom = typeArray.getDimension(
            R.styleable.TagView_textPaddingBottom,
            Utils.dipToPx(this.context, Constants.DEFAULT_TAG_TEXT_PADDING_BOTTOM).toFloat()
        )
            .toInt()
        typeArray.recycle()
    }

    /**
     * onSizeChanged
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        if (width <= 0) return
        mWidth = measuredWidth
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawTags()
    }

    /**
     * tag draw
     */
    private fun drawTags() {
        if (!mInitialized) {
            return
        }

        // clear all tag
        removeAllViews()

        // layout padding left & layout padding right
        var total = (paddingLeft + paddingRight).toFloat()
        var listIndex = 1 // List Index
        var indexBottom = 1 // The Tag to add below
        var indexHeader = 1 // The header tag of this line
        var tagPre: Tag? = null
        for (item in mTags) {
            val position = listIndex - 1

            // inflate tag layout
            val tagLayout = mInflater!!.inflate(R.layout.tagview_item, null)
            tagLayout.id = listIndex
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                tagLayout.setBackgroundDrawable(getSelector(item))
            } else {
                tagLayout.background = getSelector(item)
            }

            // tag text
            val tagView = tagLayout.findViewById<View>(R.id.tv_tag_item_contain) as TextView
            tagView.text = item.getText()
            val params = tagView.layoutParams as LinearLayout.LayoutParams
            params.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, textPaddingBottom)
            tagView.layoutParams = params
            tagView.setTextColor(item.getTagTextColor())
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.getTagTextSize())
            item.getTypeface()?.let {
                tagView.setTypeface(it)
            }
            tagLayout.setOnClickListener {
                if (mClickListener != null) {
                    mClickListener!!.onTagClick(item, position)
                }
            }
            tagLayout.setOnLongClickListener {
                if (mTagLongClickListener != null) {
                    mTagLongClickListener!!.onTagLongClick(item, position)
                }
                true
            }

            // calculate　of tag layout width
            var tagWidth =
                tagView.paint.measureText(item.getText()) + textPaddingLeft + textPaddingRight
            // tagView padding (left & right)

            // deletable text
            val deletableView = tagLayout.findViewById<View>(R.id.tv_tag_item_delete) as TextView
            if (item.isDeletable()) {
                deletableView.visibility = VISIBLE
                deletableView.text = item.getDeleteIcon()
                val offset = Utils.dipToPx(context, 2f)
                deletableView.setPadding(
                    offset,
                    textPaddingTop,
                    textPaddingRight + offset,
                    textPaddingBottom
                )
                deletableView.setTextColor(item.getDeleteIndicatorColor())
                deletableView.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.getDeleteIndicatorSize())
                item.getDeleteIconDrawable()?.let {
                    deletableView.background = it
                    val deleteViewParams = LinearLayout.LayoutParams(
                        item.getDeleteIconWidth(),
                        item.getDeleteIconHeight()
                    )
                    item.getDeleteIconMargin()?.let {
                        deleteViewParams.leftMargin = Utils.dipToPx(this.context, it.get(0).toFloat())
                        deleteViewParams.topMargin = Utils.dipToPx(this.context, it.get(1).toFloat())
                        deleteViewParams.rightMargin = Utils.dipToPx(this.context, it.get(2).toFloat())
                        deleteViewParams.bottomMargin = Utils.dipToPx(this.context, it.get(3).toFloat())
                    }
                    deletableView.layoutParams = deleteViewParams
                }
                deletableView.setOnClickListener {
                    if (mDeleteListener != null) {
                        mDeleteListener!!.onTagDeleted(this@TagView, item, position)
                    }
                }
                tagWidth += deletableView.paint.measureText(item.getDeleteIcon()) + textPaddingLeft + textPaddingRight
                // deletableView Padding (left & right)
            } else {
                deletableView.visibility = GONE
            }
            val tagParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            //add margin of each line
            tagParams.bottomMargin = lineMargin
            if (mWidth <= total + tagWidth + Utils.dipToPx(
                    this.context,
                    Constants.LAYOUT_WIDTH_OFFSET
                )
            ) {
                //need to add in new line
                if (tagPre != null) tagParams.addRule(BELOW, indexBottom)
                // initialize total param (layout padding left & layout padding right)
                total = (paddingLeft + paddingRight).toFloat()
                indexBottom = listIndex
                indexHeader = listIndex
            } else {
                //no need to new line
                tagParams.addRule(ALIGN_TOP, indexHeader)
                //not header of the line
                if (listIndex != indexHeader) {
                    tagParams.addRule(RIGHT_OF, listIndex - 1)
                    tagParams.leftMargin = tagMargin
                    total += tagMargin.toFloat()
                    if (tagPre!!.getTagTextSize() < item.getTagTextSize()) {
                        indexBottom = listIndex
                    }
                }
            }
            total += tagWidth
            addView(tagLayout, tagParams)
            tagPre = item
            listIndex++
        }
    }

    private fun getSelector(tag: Tag): Drawable? {
        if (tag.getBackground() != null) return tag.getBackground()
        val states = StateListDrawable()
        val gdNormal = GradientDrawable()
        gdNormal.setColor(tag.getLayoutColor())
        gdNormal.cornerRadius = tag.getRadius()
        if (tag.getLayoutBorderSize() > 0) {
            gdNormal.setStroke(
                Utils.dipToPx(context, tag.getLayoutBorderSize()),
                tag.getLayoutBorderColor()
            )
        }
        val gdPress = GradientDrawable()
        gdPress.setColor(tag.getLayoutColorPress())
        gdPress.cornerRadius = tag.getRadius()
        states.addState(intArrayOf(android.R.attr.state_pressed), gdPress)
        //must add state_pressed first，or state_pressed will not take effect
        states.addState(intArrayOf(), gdNormal)
        return states
    }


    //public methods
    //----------------- separator  -----------------//

    /**
     * @param tag
     */
    fun addTag(tag: Tag) {
        mTags.add(tag)
        drawTags()
    }

    fun addTags(tags: List<Tag>?) {
        if (tags == null) return
        mTags = ArrayList()
        if (tags.isEmpty()) drawTags()
        for (item in tags) {
            mTags.add(item)
        }
        drawTags()
    }


    fun addTags(tags: Array<String>) {
        for (item in tags) {
            val tag = Tag(item)
            mTags.add(tag)
        }
        drawTags()
    }

    /**
     * get tag list
     *
     * @return mTags TagObject List
     */
    fun getTags(): List<Tag> {
        return mTags
    }

    /**
     * remove tag
     *
     * @param position
     */
    fun remove(position: Int) {
        if (position < mTags.size) {
            mTags.removeAt(position)
            drawTags()
        }
    }

    /**
     * remove all views
     */
    fun removeAll() {
        mTags.clear() //clear all of tags
        removeAllViews()
    }

    fun getLineMargin(): Int {
        return lineMargin
    }

    fun setLineMargin(lineMargin: Float) {
        this.lineMargin = Utils.dipToPx(context, lineMargin)
    }

    fun getTagMargin(): Int {
        return tagMargin
    }

    fun setTagMargin(tagMargin: Float) {
        this.tagMargin = Utils.dipToPx(context, tagMargin)
    }

    fun getTextPaddingLeft(): Int {
        return textPaddingLeft
    }

    fun setTextPaddingLeft(textPaddingLeft: Float) {
        this.textPaddingLeft = Utils.dipToPx(context, textPaddingLeft)
    }

    fun getTextPaddingRight(): Int {
        return textPaddingRight
    }

    fun setTextPaddingRight(textPaddingRight: Float) {
        this.textPaddingRight = Utils.dipToPx(context, textPaddingRight)
    }

    fun getTextPaddingTop(): Int {
        return textPaddingTop
    }

    fun setTextPaddingTop(textPaddingTop: Float) {
        this.textPaddingTop = Utils.dipToPx(context, textPaddingTop)
    }

    fun gettextPaddingBottom(): Int {
        return textPaddingBottom
    }

    fun settextPaddingBottom(textPaddingBottom: Float) {
        this.textPaddingBottom = Utils.dipToPx(context, textPaddingBottom)
    }


    /**
     * setter for OnTagLongClickListener
     *
     * @param longClickListener
     */
    fun setOnTagLongClickListener(longClickListener: OnTagLongClickListener?) {
        mTagLongClickListener = longClickListener
    }

    /**
     * setter for OnTagSelectListener
     *
     * @param clickListener
     */
    fun setOnTagClickListener(clickListener: OnTagClickListener?) {
        mClickListener = clickListener
    }

    /**
     * setter for OnTagDeleteListener
     *
     * @param deleteListener
     */
    fun setOnTagDeleteListener(deleteListener: OnTagDeleteListener) {
        mDeleteListener = deleteListener
    }

    /**
     * Listeners
     */
    interface OnTagDeleteListener {
        fun onTagDeleted(view: TagView?, tag: Tag?, position: Int)
    }

    interface OnTagClickListener {
        fun onTagClick(tag: Tag?, position: Int)
    }

    interface OnTagLongClickListener {
        fun onTagLongClick(tag: Tag?, position: Int)
    }
}