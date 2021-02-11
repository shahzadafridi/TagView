# TagView Custom Android Library

## MAVEN REPOSITORY
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

## DEPENDENCY REQUIRED

```
implementation 'com.github.shahzadafridi:TagView:Tag'

```

## XML

```
  <com.realtimecoding.tagview.TagView
        android:id="@+id/tag_group"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        app:lineMargin="5dp"
        app:tagMargin="5dp"
        app:textPaddingBottom="5dp"
        app:textPaddingLeft="8dp"
        app:textPaddingRight="8dp"
        app:textPaddingTop="5dp" />

```

## KOTLIN CODE

### Intialize
```
  var tagGroup = findViewById(R.id.tag_group)
  var text = "TEST"

  var tag: Tag
        tag = Tag(text)
        tag.setRadius(6f)
        tag.setDeleteIconDrawable(ContextCompat.getDrawable(this,R.drawable.ic_launcher_background)!!)
        tag.setDeleteIconSize(120,50)
        tag.setDeleteIconMargin(intArrayOf(16,0,16,0)) //leftMargin, topMargin, rightMargin, bottomMargin
        tag.setTypeface(ResourcesCompat.getFont(this,R.font.test)!!)
        tag.setLayoutColor(Color.parseColor("#1E1D1B"))
        tag.setDeletable(true)

        tagGroup!!.addTag(tag)

```

### LISTENERS
you can perform click event action such delete and click.

#### CLICK TAG

```
  tagGroup!!.setOnTagClickListener(object : TagView.OnTagClickListener{
      override fun onTagClick(tag: Tag?, position: Int) {
          Toast.makeText(this@MainActivity, "\"" + tag!!.getText().toString() + "\" clicked", Toast.LENGTH_SHORT).show()
      }

  })
```

#### DELETE TAG

```
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
```


