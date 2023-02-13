package com.kpfu.kfutimetable.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.kpfu.kfutimetable.presentation.base.utils.BaseState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<S : BaseState, VS : BaseViewState>(
    initialState: () -> S,
    private val viewStateMapper: BaseViewStateMapper<S, VS>
) : ViewModel() {

    var state: S = initialState()
        set(value) {
            field = value
            _viewStateFlow.value = viewStateMapper.mapToViewState(value)
        }
    private val _viewStateFlow = MutableStateFlow(viewStateMapper.mapToViewState(initialState()))
    val viewStateFlow = _viewStateFlow.asStateFlow()
}

fun interface BaseViewModelProvider<T> {

    fun createInstance(owner: ViewModelStoreOwner): T
}