package com.realtimecoding.tagview

import android.content.Context
import android.util.TypedValue

object Utils {

    @Throws(InstantiationException::class)
    private fun Utils() {
        throw InstantiationException("This class is not for instantiation")
    }

    fun dipToPx(c: Context, dipValue: Float): Int {
        val metrics = c.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics).toInt()
    }

}