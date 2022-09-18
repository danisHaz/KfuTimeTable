package com.kpfu.kfutimetable.presentation.base.flowstates

import java.lang.Exception

sealed interface BaseFlowState {
    object Loading : BaseFlowState

    data class Error(val e: Exception, val message: String? = null) : BaseFlowState

    data class Success<T>(val data: T) : BaseFlowState
}