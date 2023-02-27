package com.kpfu.kfutimetable.presentation.mainscreen.entities

import com.kpfu.kfutimetable.presentation.base.utils.BaseState

data class CalendarState(
    val lessons: List<Lesson>
) : BaseState

data class Lesson(
    val lessonName: String,
    val lessonType: LessonType,
    val teacherName: String,
    val address: String,
    val classroom: String,
    val day: String,
    val startTime: String,
    val weekFilters: List<String>
)

enum class LessonType {
    Lecture, Seminar;
}