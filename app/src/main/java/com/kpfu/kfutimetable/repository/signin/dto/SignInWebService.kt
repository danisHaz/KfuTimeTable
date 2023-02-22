package com.kpfu.kfutimetable.repository.signin.dto

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInWebService {

    @POST("/signin")
    fun signInUser(
        @Body userAuthData: UserAuthData
    ): Call<UniversityGroupData>
}