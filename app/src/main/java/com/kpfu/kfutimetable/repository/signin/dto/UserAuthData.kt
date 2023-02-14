package com.kpfu.kfutimetable.repository.signin.dto

import com.google.gson.annotations.SerializedName

data class UserAuthData(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String,
)

data class AuthResponse(
    @SerializedName("data")
    val groupNumber: String,
)