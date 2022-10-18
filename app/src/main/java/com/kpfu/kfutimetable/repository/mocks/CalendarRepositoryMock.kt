package com.kpfu.kfutimetable.repository.mocks

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.Lesson
import com.kpfu.kfutimetable.repository.main.CalendarRepository
import java.util.Date

class CalendarRepositoryMock : CalendarRepository {
    override suspend fun getCalendarStateByDate(date: Date) = CalendarState(
        date,
        listOf(
            Lesson("Math"),
            Lesson("Programming"),
            Lesson("Chill")
        )
    )

}