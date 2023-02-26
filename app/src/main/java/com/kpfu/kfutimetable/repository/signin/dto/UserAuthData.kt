package com.kpfu.kfutimetable.repository.signin.dto

import com.google.gson.annotations.SerializedName

data class UserAuthData(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String,
)

data class UniversityGroupData (
    @SerializedName("data")
    val groupNumber: String,
    @SerializedName("error")
    val hasError: Boolean,
    @SerializedName("name")
    val userName: String,
    @SerializedName("surname")
    val userSurname: String,
)