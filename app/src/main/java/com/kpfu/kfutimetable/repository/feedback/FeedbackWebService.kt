package com.kpfu.kfutimetable.repository.feedback

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackWebService {

    @POST("/faq")
    fun postReport(
        @Body report: FeedbackDto
    ): Call<Unit>
}