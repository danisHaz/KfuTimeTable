package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import java.util.Date

interface CalendarRepository {

    suspend fun getCalendarStateByDate(date: Date): CalendarState
}