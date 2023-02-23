package com.kpfu.kfutimetable.utils

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.StyleableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LifecycleCoroutineScope
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.presentation.base.utils.BaseApplication
import com.kpfu.kfutimetable.presentation.mainscreen.entities.Lesson
import com.kpfu.kfutimetable.repository.main.dto.LessonsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.temporal.WeekFields
import java.util.*
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

fun Context.getColorStateList(
    attributes: TypedArray, @StyleableRes index: Int
): ColorStateList? {
    if (attributes.hasValue(index)) {
        val resourceId = attributes.getResourceId(index, 0)
        if (resourceId != 0) {
            val value = AppCompatResources.getColorStateList(this, resourceId)
            if (value != null) {
                return value
            }
        }
    }

    return attributes.getColorStateList(index)
}

fun Color.changeAlpha(alphaCoef: Float = 0.1f): Color {
    val alpha = alpha() * alphaCoef
    return Color.valueOf(Color.argb(alpha, red(), green(), blue()))
}

fun DayOfWeek.toString(context: Context?): String {
    val daysNamesList = context?.resources?.getStringArray(R.array.days_short)
        ?: error("context is null")
    return daysNamesList[this.value - 1]
}

fun Month.toString(context: Context?): String {
    val monthsNamesList = context?.resources?.getStringArray(R.array.months)
        ?: error("context is null")
    term1.plus(term2).forEachIndexed { index, month ->
        if (this.value == month.value) {
            return monthsNamesList[index]
        }
    }

    error("Month is not declared")
}

fun getDayOfWeekFrom(context: Context?, dayOfWeek: String): DayOfWeek {
    val monthNamesList = context?.resources?.getStringArray(R.array.daysOfWeek)
        ?: error("context is null")
    monthNamesList.forEachIndexed { ind, day ->
        if (day.lowercase() == dayOfWeek.lowercase()) {
            return DayOfWeek.of(ind+1)
        }
    }

    error("dayOfWeek is inappropriate")
}

fun LessonsDto.filterByDay(dayOfWeek: DayOfWeek, context: Context?): List<Lesson> {
    val evenWeek = lessonsForEvenWeek.filter { getDayOfWeekFrom(context, it.day) == dayOfWeek }
        .map {
            it.toLesson()
        }
    val oddWeek = lessonsForOddWeek.filter { getDayOfWeekFrom(context, it.day) == dayOfWeek }.map {
        it.toLesson()
    }

    return evenWeek.plus(oddWeek)
}

private fun getTypeOfWeek(day: LocalDate, firstDayOfSemestr: LocalDate): Int {
    val weekFields = WeekFields.of(Locale.getDefault())
    val weekNumberForCurDay = day.get(weekFields.weekOfWeekBasedYear())
    val weekNumberForFirstDay = firstDayOfSemestr.get(weekFields.weekOfWeekBasedYear())
    return (weekNumberForCurDay - weekNumberForFirstDay + 1) % 2
}