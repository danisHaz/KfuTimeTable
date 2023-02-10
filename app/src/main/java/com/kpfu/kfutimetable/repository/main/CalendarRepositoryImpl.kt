package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.WeekDay
import com.kpfu.kfutimetable.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.await
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarWebService: CalendarWebService,
) : CalendarRepository {

    override suspend fun getLessonsByDay(dayOfWeek: WeekDay): Flow<ResultState<CalendarState>> =
        flow {
            emit(ResultState(isLoading = true))

            var data: CalendarState? = null
            try {
                data = calendarWebService.getLessonsByDay(dayOfWeek).await()
            } catch (e: HttpException) {
                emit(ResultState(error = e))
            }

            emit(ResultState(data = data))
        }

    override suspend fun getLessonsForWeek(): Flow<ResultState<List<CalendarState>>> = flow {
        emit(ResultState(isLoading = true))

        var data: List<CalendarState>? = null
        try {
            data = calendarWebService.getLessonsForWeek().await()
        } catch (e: HttpException) {
            emit(ResultState(error = e))
        }

        emit(ResultState(data = data))
    }
}