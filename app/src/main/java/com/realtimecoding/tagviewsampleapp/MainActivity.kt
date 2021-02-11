package com.realtimecoding.tagviewsampleapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.realtimecoding.tagview.Tag
import com.realtimecoding.tagview.TagView

class MainActivity : AppCompatActivity() {

    var tagList: MutableList<Tag> = arrayListOf()
    var tagGroup: TagView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tagGroup = findViewById(R.id.tag_group)

        tagGroup!!.setOnTagDeleteListener(object: TagView.OnTagDeleteListener{
            override fun onTagDeleted(view: TagView?, tag: Tag?, position: Int) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("\"" + tag!!.getText().toString() + "\" will be delete. Are you sure?")
                builder.setPositiveButton("Yes", { dialog, which ->
                    view!!.remove(position)
                    Toast.makeText(this@MainActivity, "\"" + tag.getText().toString() + "\" deleted", Toast.LENGTH_SHORT).show()
                })
                builder.setNegativeButton("No", null)
                builder.show()
            }

        })

        tagGroup!!.setOnTagClickListener(object : TagView.OnTagClickListener{
            override fun onTagClick(tag: Tag?, position: Int) {
                Toast.makeText(this@MainActivity, "\"" + tag!!.getText().toString() + "\" clicked", Toast.LENGTH_SHORT).show()
            }

        })

        setTags("TEST")
    }

    private fun setTags(cs: CharSequence) {

        /**
         * for empty edittext
         */

        if (cs.toString() == "") {
            tagGroup!!.addTags(arrayListOf())
            return
        }

        val text = cs.toString()
        var tag: Tag
        tag = Tag(text)
        tag.setRadius(6f)
        tag.setDeletable(true)
        tagList.add(tag)
        tagGroup!!.addTags(tagList)
    }
}