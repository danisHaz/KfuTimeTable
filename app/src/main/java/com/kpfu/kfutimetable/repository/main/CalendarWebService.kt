package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.WeekDay
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CalendarWebService {

    @POST("/getLessonsByDay")
    fun getLessonsByDay(
        @Body dayOfWeek: WeekDay
    ): Call<CalendarState>

    @GET("/getLessonsForWeek")
    fun getLessonsForWeek(): Call<List<CalendarState>>
}