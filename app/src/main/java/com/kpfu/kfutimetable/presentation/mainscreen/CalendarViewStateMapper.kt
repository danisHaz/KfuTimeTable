package com.kpfu.kfutimetable.presentation.mainscreen

import com.kpfu.kfutimetable.commonwidgets.SubjectView
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.LessonType
import java.util.*

class CalendarViewStateMapper : BaseViewStateMapper<CalendarState, CalendarViewState> {
    override fun mapToViewState(state: CalendarState): CalendarViewState {
        val subjectList = mutableListOf<SubjectView.State>().apply {
            for (lesson in state.lessons) {
                val (hours, minutes) = lesson.startTime.split("-")[0].split(".")
                    .map { Integer.parseInt(it.trim()) }

                add(
                    SubjectView.State(
                        subject = lesson.lessonName,
                        subjectType = when (lesson.lessonType) {
                            LessonType.Lecture -> SubjectView.State.SubjectType.Lecture
                            LessonType.Seminar -> SubjectView.State.SubjectType.Seminar
                        },
                        address = lesson.address,
                        teacherName = lesson.teacherName,
                        roomNumber = lesson.classroom,
                        startTime = Calendar.Builder().set(Calendar.HOUR_OF_DAY, hours)
                            .set(Calendar.MINUTE, minutes)
                            .build()
                    )
                )
            }
        }
        return CalendarViewState(listOf(), listOf(subjectList))
    }
}