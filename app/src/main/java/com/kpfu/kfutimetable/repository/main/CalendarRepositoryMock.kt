package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.Lesson
import com.kpfu.kfutimetable.presentation.mainscreen.entities.LessonType
import com.kpfu.kfutimetable.presentation.mainscreen.entities.WeekDay
import com.kpfu.kfutimetable.repository.main.CalendarRepository
import com.kpfu.kfutimetable.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class CalendarRepositoryMock : CalendarRepository {
    override fun getLessonsByDay(date: String): Flow<ResultState<CalendarState>> =
        flow {
            emit(
                ResultState(
                    data = CalendarState(
                        date,
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                            )
                        )
                    )
                )
            )
        }

    override fun getLessonsForWeek(): Flow<ResultState<List<CalendarState>>> = flow {
        emit(
            ResultState(
                data = listOf(
                    CalendarState(
                        "2023-02-11 13:24:45",
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                            )
                        )
                    ),
                    CalendarState(
                        "2023-02-11 13:24:45",
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                            )
                        )
                    ),
                    CalendarState(
                        "2023-02-11 13:24:45",
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                            )
                        )
                    ),
                    CalendarState(
                        "2023-02-11 13:24:45",
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                            )
                        )
                    ),
                    CalendarState(
                        "2023-02-11 13:24:45",
                        listOf(
                            Lesson(
                                "(Л) Математический анализ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                "12:10",
                            )
                        )
                    ),
                )
            )
        )
    }

    override fun getCurrentMonths(): Flow<ResultState<List<Date>>> = flow {

    }

}