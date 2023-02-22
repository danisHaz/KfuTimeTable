package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.utils.ResultState
import kotlinx.coroutines.flow.Flow
import java.time.Month

interface CalendarRepository {

    fun getLessonsByDay(date: String): Flow<ResultState<CalendarState>>
    fun getLessonsForWeek(universityGroupNumber: String): Flow<ResultState<List<CalendarState>>>
    fun getCurrentMonths(): Flow<ResultState<List<Month>>>
}