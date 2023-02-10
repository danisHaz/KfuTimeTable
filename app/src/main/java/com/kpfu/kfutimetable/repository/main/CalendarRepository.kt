package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.WeekDay
import com.kpfu.kfutimetable.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {

    suspend fun getLessonsByDay(dayOfWeek: WeekDay): Flow<ResultState<CalendarState>>
    suspend fun getLessonsForWeek(): Flow<ResultState<List<CalendarState>>>
}