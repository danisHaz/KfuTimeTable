package com.kpfu.kfutimetable.presentation.base

import androidx.lifecycle.ViewModel
import com.kpfu.kfutimetable.presentation.base.utils.BaseState

abstract class BaseViewModel<S : BaseState>(
    initialState: () -> S,
) : ViewModel() {

    private var state: S = initialState()
}