package com.kpfu.kfutimetable.repository.main

import com.kpfu.kfutimetable.repository.main.dto.LessonsDto
import com.kpfu.kfutimetable.repository.main.dto.SubjectDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.time.Month

interface CalendarWebService {

    @POST("/getLessonsByDay")
    fun getLessonsByDay(
        @Body date: String
    ): Call<SubjectDto>

    @GET("/getLessonsForWeek")
    fun getLessonsForWeek(): Call<LessonsDto>

    @GET("/getCurrentMonths")
    fun getCurrentMonths(): Call<List<Month>>
}