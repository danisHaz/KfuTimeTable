package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.Lesson
import com.kpfu.kfutimetable.presentation.mainscreen.entities.LessonType
import com.kpfu.kfutimetable.repository.main.dto.LessonsDto
import com.kpfu.kfutimetable.repository.main.dto.SubjectDto
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
                                    weekFilters = listOf()
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
                                    weekFilters = listOf()
                                )
                            )
                        )
                    )
                )
            }
            changeEmulatorField = !changeEmulatorField
        }

    override fun getLessonsForWeek(universityGroupNumber: String): Flow<ResultState<LessonsDto>> =
        flow {
            emit(
                ResultState(
                    data = LessonsDto(
                        lessonsForEvenWeek = listOf(
                            SubjectDto(
                                name = "(Л) Оптимизация АААААА",
                                teacherName = "Асхатов Р.М.",
                                address = "ул. Кремлевская, 35",
                                classroom = "ауд. 216",
                                day = "понедельник",
                                time = "12.10-13.40",
                                additionalWeekFilters = listOf()
                            ),
                            SubjectDto(
                                name = "(Л) Оптимизация fjksdfljs",
                                teacherName = "Асхатов Р.М.",
                                address = "ул. Кремлевская, 35",
                                classroom = "ауд. 220",
                                day = "вторник",
                                time = "12.10-13.40",
                                additionalWeekFilters = listOf("3-7 неделя")
                            ),
                            SubjectDto(
                                name = "(Л) Оптимизация DDDDDDDDDD",
                                teacherName = "Асхатов Р.М.",
                                address = "ул. Кремлевская, 35",
                                classroom = "ауд. 216",
                                day = "четверг",
                                time = "12.10-13.40",
                                additionalWeekFilters = listOf("03.03.2023-30.03.2023")
                            ),
                        ),
                        lessonsForOddWeek = listOf(
                            SubjectDto(
                                name = "(Л) Оптимизация АААААА",
                                teacherName = "Асхатов Р.М.",
                                address = "ул. Кремлевская, 35",
                                classroom = "ауд. 216",
                                day = "понедельник",
                                time = "12.10-13.40",
                                additionalWeekFilters = listOf()
                            ),
                            SubjectDto(
                                name = "(Л) Оптимизация fjksdfljs",
                                teacherName = "Асхатов Р.М.",
                                address = "ул. Кремлевская, 35",
                                classroom = "ауд. 220",
                                day = "вторник",
                                time = "12.10-13.40",
                                additionalWeekFilters = listOf("3-7 неделя")
                            ),
                            SubjectDto(
                                name = "(Л) Оптимизация DDDDDDDDDD",
                                teacherName = "Асхатов Р.М.",
                                address = "ул. Кремлевская, 35",
                                classroom = "ауд. 216",
                                day = "четверг",
                                time = "12.10-13.40",
                                additionalWeekFilters = listOf("03.03.2023-30.03.2023")
                            ),
                            SubjectDto(
                                name = "(Л) Pain, without love",
                                teacherName = "Асхатов Р.М.",
                                address = "ул. Кремлевская, 35",
                                classroom = "ауд. 216",
                                day = "пятница",
                                time = "12.10-13.40",
                                additionalWeekFilters = listOf("1-10 неделя")
                            ),
                            SubjectDto(
                                name = "(Л) Pain, can't get enough",
                                teacherName = "Асхатов Р.М.",
                                address = "ул. Кремлевская, 35",
                                classroom = "ауд. 220",
                                day = "суббота",
                                time = "12.10-13.40",
                                additionalWeekFilters = listOf("2-5 неделя")
                            ),
                            SubjectDto(
                                name = "(Л) Pain, I like it rough",
                                teacherName = "Асхатов Р.М.",
                                address = "ул. Кремлевская, 35",
                                classroom = "ауд. 216",
                                day = "понедельник",
                                time = "17.30-19.00",
                                additionalWeekFilters = listOf("28.02.2023-28.03.2023")
                            ),
                        ),

                    )
                )
            )
        }

    override fun getCurrentMonths(): Flow<ResultState<List<Month>>> = flow {
        emit(ResultState(data = listOf(Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY)))
    }

}