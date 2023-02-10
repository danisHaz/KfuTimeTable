package com.kpfu.kfutimetable.presentation.mainscreen.entities

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.presentation.base.utils.BaseState

data class CalendarState(
    @SerializedName("day")
    val dayOfWeek: WeekDay,
    @SerializedName("lessons")
    val lessons: List<Lesson>,
) : BaseState

data class Lesson(
    @SerializedName("lesson")
    val lessonName: String,
    @SerializedName("teacher")
    val teacherName: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("classroom")
    val classroom: String,
    @SerializedName("time_start")
    val startTime: String,
    @SerializedName("time_end")
    val endTime: String,
)

enum class WeekDay {
    Mon, Tue, Wen, Thu, Fri, Sat, Sun;

    fun toString(context: Context): String {
        val days = context.resources.getStringArray(R.array.daysOfWeek)
        return when (this) {
            Mon -> days[0]
            Tue -> days[1]
            Wen -> days[2]
            Thu -> days[3]
            Fri -> days[4]
            Sat -> days[5]
            Sun -> days[6]
        }
    }
}