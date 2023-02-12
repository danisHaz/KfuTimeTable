package com.kpfu.kfutimetable.presentation.mainscreen.entities

import com.google.gson.annotations.SerializedName
import com.kpfu.kfutimetable.presentation.base.utils.BaseState

data class CalendarState(
    @SerializedName("date")
    val date: String,
    @SerializedName("lessons")
    val lessons: List<Lesson>,
) : BaseState

data class Lesson(
    @SerializedName("lesson")
    val lessonName: String,
    @SerializedName("lesson_type")
    val lessonType: LessonType,
    @SerializedName("teacher")
    val teacherName: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("classroom")
    val classroom: String,
    @SerializedName("time_start")
    val startTime: String,
)

enum class LessonType {
    Lecture, Seminar;
}