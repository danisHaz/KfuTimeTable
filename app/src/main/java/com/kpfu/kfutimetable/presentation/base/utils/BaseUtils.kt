package com.kpfu.kfutimetable.presentation.base.utils

interface BaseState

interface BaseViewState

fun interface BaseViewStateMapper<S : BaseState, VS : BaseViewState> {

    fun mapToViewState(state: S): VS
}