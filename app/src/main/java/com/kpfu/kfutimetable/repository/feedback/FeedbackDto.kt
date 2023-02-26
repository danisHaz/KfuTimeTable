package com.kpfu.kfutimetable.repository.feedback

import com.google.gson.annotations.SerializedName

data class FeedbackDto(
    @SerializedName("report")
    val report: String
)
