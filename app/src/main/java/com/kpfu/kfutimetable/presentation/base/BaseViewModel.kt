package com.kpfu.kfutimetable.presentation.base

import androidx.lifecycle.ViewModel
import com.kpfu.kfutimetable.presentation.base.utils.BaseState
import com.kpfu.kfutimetable.presentation.base.utils.EmptyState

abstract class BaseViewModel<S : BaseState>(
    initialState: (() -> S)?,
) : ViewModel() {

    var state: S = initialState?.invoke()
}

fun interface BaseViewModelProvider<T> {

    fun createInstance(): T
}