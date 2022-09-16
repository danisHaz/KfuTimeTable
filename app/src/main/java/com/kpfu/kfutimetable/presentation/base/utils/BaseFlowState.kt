package com.kpfu.kfutimetable.presentation.base.utils

import java.lang.Exception

sealed interface BaseFlowState {
    object Loading : BaseFlowState

    data class Error(val e: Exception, val message: String? = null)

    data class Success<T>(val data: T)
}