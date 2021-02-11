package com.realtimecoding.tagview

import android.graphics.Typeface
import android.graphics.drawable.Drawable

class Tag {

    private var id = 0
    private var text: String? = null
    private var tagTextColor = 0
    private var tagTextSize = 0f
    private var layoutColor = 0
    private var layoutColorPress = 0
    private var isDeletable = false
    private var deleteIndicatorColor = 0
    private var deleteIndicatorSize = 0f
    private var deleteIconMargin: IntArray? = null
    private var radius = 0f
    private var deleteIcon: String? = null
    private var deleteIconDrawable: Drawable? = null
    private var deleteIconWidth = 0
    private var deleteIconHeight = 0
    private var layoutBorderSize = 0f
    private var layoutBorderColor = 0
    private var background: Drawable? = null
    private var typeFace: Typeface? = null


    constructor(text: String) {
        builder(
            0,
            text,
            Constants.DEFAULT_TAG_TEXT_COLOR,
            Constants.DEFAULT_TAG_TEXT_SIZE,
            Constants.DEFAULT_TAG_LAYOUT_COLOR,
            Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
            Constants.DEFAULT_TAG_IS_DELETABLE,
            Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR,
            Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE,
            Constants.DEFAULT_TAG_RADIUS,
            Constants.DEFAULT_TAG_DELETE_ICON,
            Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE,
            Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR
        )
    }

    private fun builder(){

    }

    private fun builder(
        id: Int, text: String, tagTextColor: Int, tagTextSize: Float,
        layoutColor: Int, layoutColorPress: Int, isDeletable: Boolean,
        deleteIndicatorColor: Int, deleteIndicatorSize: Float, radius: Float,
        deleteIcon: String, layoutBorderSize: Float, layoutBorderColor: Int
    ) {
        this.id = id
        this.text = text
        this.tagTextColor = tagTextColor
        this.tagTextSize = tagTextSize
        this.layoutColor = layoutColor
        this.layoutColorPress = layoutColorPress
        this.isDeletable = isDeletable
        this.deleteIndicatorColor = deleteIndicatorColor
        this.deleteIndicatorSize = deleteIndicatorSize
        this.radius = radius
        this.deleteIcon = deleteIcon
        this.layoutBorderSize = layoutBorderSize
        this.layoutBorderColor = layoutBorderColor
    }

    fun setTagTextColor(tagTextColor: Int) {
        this.tagTextColor = tagTextColor
    }

    fun setTagTextSize(tagTextSize: Float) {
        this.tagTextSize = tagTextSize
    }

    fun setLayoutColor(layoutColor: Int) {
        this.layoutColor = layoutColor
    }

    fun setLayoutColorPress(layoutColorPress: Int) {
        this.layoutColorPress = layoutColorPress
    }

    fun setDeletable(deletable: Boolean) {
        isDeletable = deletable
    }

    fun setDeleteIndicatorColor(deleteIndicatorColor: Int) {
        this.deleteIndicatorColor = deleteIndicatorColor
    }

    fun setDeleteIndicatorSize(deleteIndicatorSize: Float) {
        this.deleteIndicatorSize = deleteIndicatorSize
    }

    fun setRadius(radius: Float) {
        this.radius = radius
    }

    fun setDeleteIconDrawable(drawable: Drawable){
        this.deleteIconDrawable = drawable
    }

    fun getDeleteIconDrawable(): Drawable? {
        return this.deleteIconDrawable
    }

    fun setDeleteIcon(deleteIcon: String?) {
        this.deleteIcon = deleteIcon
    }

    fun setDeleteIconSize(width: Int, height: Int){
        this.deleteIconWidth = width
        this.deleteIconHeight = height
    }

    fun getDeleteIconWidth(): Int {
        return this.deleteIconWidth
    }

    fun getDeleteIconHeight(): Int {
        return this.deleteIconHeight
    }

    fun setDeleteIconMargin(margin: IntArray) {
        this.deleteIconMargin = margin
    }

    fun getDeleteIconMargin(): IntArray? {
        return this.deleteIconMargin
    }

    fun setLayoutBorderSize(layoutBorderSize: Float) {
        this.layoutBorderSize = layoutBorderSize
    }

    fun setLayoutBorderColor(layoutBorderColor: Int) {
        this.layoutBorderColor = layoutBorderColor
    }

    fun setBackground(background: Drawable?) {
        this.background = background
    }

    fun setTypeface(font: Typeface){
        this.typeFace = font
    }

    fun getTypeface(): Typeface? {
        return this.typeFace
    }

    fun getText(): String? {
        return text
    }

    fun getTagTextColor(): Int {
        return tagTextColor
    }

    fun getTagTextSize(): Float {
        return tagTextSize
    }

    fun getLayoutColor(): Int {
        return layoutColor
    }

    fun getLayoutColorPress(): Int {
        return layoutColorPress
    }

    fun isDeletable(): Boolean {
        return isDeletable
    }

    fun getDeleteIndicatorColor(): Int {
        return deleteIndicatorColor
    }

    fun getDeleteIndicatorSize(): Float {
        return deleteIndicatorSize
    }

    fun getRadius(): Float {
        return radius
    }

    fun getDeleteIcon(): String? {
        return deleteIcon
    }

    fun getLayoutBorderSize(): Float {
        return layoutBorderSize
    }

    fun getLayoutBorderColor(): Int {
        return layoutBorderColor
    }

    fun getBackground(): Drawable? {
        return background
    }
}