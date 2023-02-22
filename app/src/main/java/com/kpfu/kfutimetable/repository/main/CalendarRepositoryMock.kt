package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.Lesson
import com.kpfu.kfutimetable.presentation.mainscreen.entities.LessonType
import com.kpfu.kfutimetable.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.DayOfWeek
import java.time.Month
import java.time.MonthDay

class CalendarRepositoryMock : CalendarRepository {

    private var changeEmulatorField = false
    override fun getLessonsByDay(date: String): Flow<ResultState<CalendarState>> =
        flow {
            if (changeEmulatorField) {
                emit(
                    ResultState(
                        data = CalendarState(
                            listOf(
                                Lesson(
                                    "(Л) Математический анализ",
                                    LessonType.Lecture,
                                    "Асхатов Р.М.",
                                    "ул. Кремлевская, 35",
                                    "ауд. 216",
                                    DayOfWeek.MONDAY.toString(),
                                    "12:10",
                                )
                            )
                        )
                    )
                )
            } else {
                emit(
                    ResultState(
                        data = CalendarState(
                            listOf(
                                Lesson(
                                    "(П) ЫЫЫЫЫЫЫЫ",
                                    LessonType.Seminar,
                                    "Мемный чел",
                                    "ул. Кремлевская, 3132",
                                    "ауд. 1010101",
                                    DayOfWeek.SATURDAY.toString(),
                                    "15:20",
                                )
                            )
                        )
                    )
                )
            }
            changeEmulatorField = !changeEmulatorField
        }

    override fun getLessonsForWeek(): Flow<ResultState<List<CalendarState>>> = flow {
        emit(
            ResultState(
                data = listOf(
                    CalendarState(
                        listOf(
                            Lesson(
                                "(Л) Оптимизация АААААА",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                DayOfWeek.MONDAY.toString(),
                                "12:10",
                            )
                        )
                    ),
                    CalendarState(
                        listOf(
                            Lesson(
                                "(Л) Оптимизация ББББББ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                DayOfWeek.TUESDAY.toString(),
                                "12:10",
                            )
                        )
                    ),
                    CalendarState(
                        listOf(
                            Lesson(
                                "(Л) Оптимизация ВВВВВВВВ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                DayOfWeek.WEDNESDAY.toString(),
                                "12:10",
                            )
                        )
                    ),
                    CalendarState(
                        listOf(
                            Lesson(
                                "(Л) Оптимизация ГГГГГГГ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                DayOfWeek.THURSDAY.toString(),
                                "12:10",
                            )
                        )
                    ),
                    CalendarState(
                        listOf(
                            Lesson(
                                "(Л) Оптимизация ДДДДДДД",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                DayOfWeek.FRIDAY.toString(),
                                "12:10",
                            )
                        )
                    ),
                    CalendarState(
                        listOf(
                            Lesson(
                                "(Л) Оптимизация УУУУУУУ",
                                LessonType.Lecture,
                                "Асхатов Р.М.",
                                "ул. Кремлевская, 35",
                                "ауд. 216",
                                DayOfWeek.SATURDAY.toString(),
                                "12:10",
                            )
                        )
                    ),
                )
            )
        )
    }

    override fun getCurrentMonths(): Flow<ResultState<List<Month>>> = flow {
        emit(ResultState(data = listOf(Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY)))
    }

}