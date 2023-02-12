package com.kpfu.kfutimetable.utils

import java.time.LocalDate

val BASE_URL = "base-url.com"

data class LocalDateWrapper<AdditionalParam>(
    val date: LocalDate,
    val param: AdditionalParam
)