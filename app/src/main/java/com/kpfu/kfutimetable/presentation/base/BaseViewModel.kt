package com.kpfu.kfutimetable.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.kpfu.kfutimetable.presentation.base.utils.BaseState

abstract class BaseViewModel<S : BaseState>(
    initialState: () -> S,
) : ViewModel() {

    var state: S = initialState()
}

fun interface BaseViewModelProvider<T> {

    fun createInstance(owner: ViewModelStoreOwner): T
}