package com.kpfu.kfutimetable.utils

import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import com.kpfu.kfutimetable.presentation.base.utils.BaseApplication
import kotlin.math.roundToInt

private val displayMetrics: DisplayMetrics? by lazy {
    BaseApplication.appDisplayMetrics ?: run {
        Log.e("ScreenDensity", "Screen density variable is not initialized")
        null
    }
}

private val screenDensity: Float by lazy {
    displayMetrics?.density ?: DisplayMetrics.DENSITY_DEFAULT.toFloat()
}

val Int.dpToPxF: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        displayMetrics
    )
val Int.dpToPx: Int get() = this.dpToPxF.roundToInt()

val Float.dpToPxF: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        displayMetrics
    )

val Float.dpToPx: Int get() = this.dpToPxF.roundToInt()

val Int.pxToDpF: Float get() = (this / screenDensity)

val Int.pxToDp: Int get() = this.pxToDpF.roundToInt()

val Float.pxToDpF: Float get() = this / screenDensity

val Float.pxToDp: Int get() = this.pxToDpF.roundToInt()