package com.kpfu.kfutimetable.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kpfu.kfutimetable.presentation.base.utils.BaseState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper

open class BaseFragment<S : BaseState, VS : BaseViewState, VM : BaseViewModel<S>>(
    initialViewState: (() -> VS)?,
    viewModelProvider: BaseViewModelProvider<VM>,
    private val viewStateMapper: BaseViewStateMapper<S, VS>
) : Fragment() {

    protected val viewModel: VM = viewModelProvider.createInstance()

    protected var viewState: VS? = initialViewState?.invoke()
        get() = viewStateMapper.mapToViewState(viewModel.state)
}