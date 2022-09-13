package com.kpfu.kfutimetable.presentation.mainscreen.entities

import com.kpfu.kfutimetable.presentation.base.utils.BaseState
import java.util.Date

data class CalendarState(
    val date: Date,
    val lessons: List<Lesson>
) : BaseState

data class Lesson(
    val name: String,
)