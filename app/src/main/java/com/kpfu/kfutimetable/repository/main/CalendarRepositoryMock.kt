package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.Lesson
import com.kpfu.kfutimetable.presentation.mainscreen.entities.WeekDay
import com.kpfu.kfutimetable.repository.main.CalendarRepository
import com.kpfu.kfutimetable.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class CalendarRepositoryMock : CalendarRepository {
    override suspend fun getLessonsByDay(dayOfWeek: WeekDay): Flow<ResultState<CalendarState>> =
        flow {
            emit(
                ResultState(
                    data = CalendarState(
                        WeekDay.Mon,
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                                "13:50"
                            )
                        )
                    )
                )
            )
        }

    override suspend fun getLessonsForWeek(): Flow<ResultState<List<CalendarState>>> = flow {
        emit(
            ResultState(
                data = listOf(
                    CalendarState(
                        WeekDay.Mon,
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                                "13:50"
                            )
                        )
                    ),
                    CalendarState(
                        WeekDay.Mon,
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                                "13:50"
                            )
                        )
                    ),
                    CalendarState(
                        WeekDay.Mon,
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                                "13:50"
                            )
                        )
                    ),
                    CalendarState(
                        WeekDay.Mon,
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                                "13:50"
                            )
                        )
                    ),
                    CalendarState(
                        WeekDay.Mon,
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                                "13:50"
                            )
                        )
                    ),
                )
            )
        )
    }

}