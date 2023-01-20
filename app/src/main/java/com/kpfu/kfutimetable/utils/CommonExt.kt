package com.kpfu.kfutimetable.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleCoroutineScope
import com.kpfu.kfutimetable.presentation.base.utils.BaseApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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

fun <T> Flow<T>.launchWhenStarted(scope: LifecycleCoroutineScope) {
    scope.launchWhenStarted {
        this@launchWhenStarted.collect()
    }
}

fun Context.fromAttr(@AttrRes attrId: Int): Int? {
    val typedValue = TypedValue()
    return if (theme.resolveAttribute(attrId, typedValue, true)) {
        typedValue.data
    } else {
      Log.e("ColorExt", "Color not found for resource")
      null
    }
}

fun Color.changeAlpha(alphaCoef: Float = 0.1f): Color {
    val alpha = alpha() * alphaCoef
    return Color.valueOf(Color.argb(alpha, red(), green(), blue()))
}