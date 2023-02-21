package com.kpfu.kfutimetable.repository.main.dto

import com.google.gson.annotations.SerializedName

data class SubjectDto(
    @SerializedName("day")
    val day: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("subject")
    val name: String,
    @SerializedName("teacher")
    val teacherName: String,
    @SerializedName("classroom")
    val classroom: String,
    @SerializedName("address")
    val address: String
)

