package com.kpfu.kfutimetable.repository.main.dto

import com.google.gson.annotations.SerializedName
import com.kpfu.kfutimetable.presentation.mainscreen.entities.Lesson
import com.kpfu.kfutimetable.presentation.mainscreen.entities.LessonType

data class LessonsDto(
    @SerializedName("Even")
    val lessonsForEvenWeek: List<SubjectDto>,
    @SerializedName("Odd")
    val lessonsForOddWeek: List<SubjectDto>,
)

data class SubjectDto(
    @SerializedName("day")
    val day: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("subject")
    val name: String,
    @SerializedName("teacher")
    val teacherName: String,
    @SerializedName("classroom")
    val classroom: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("start")
    val startTime: String
) {
    fun toLesson(): Lesson = Lesson(
        lessonName = name,
        lessonType = LessonType.Lecture,
        teacherName = teacherName,
        address = address,
        classroom = classroom,
        day = day,
        startTime = startTime
    )
}

