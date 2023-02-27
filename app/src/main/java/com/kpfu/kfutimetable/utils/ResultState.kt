package com.kpfu.kfutimetable.utils

data class ResultState<out T: Any?>(
   val data: T? = null,
   val isLoading: Boolean = false,
   val error: Exception? = null
)