package com.kpfu.kfutimetable.utils

import java.time.LocalDate

val BASE_URL = "http://192.168.236.217:5050"

data class LocalDateWrapper<AdditionalParam>(
    val date: LocalDate,
    val param: AdditionalParam
)