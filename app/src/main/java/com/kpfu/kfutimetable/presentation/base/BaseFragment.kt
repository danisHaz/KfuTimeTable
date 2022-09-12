package com.kpfu.kfutimetable.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kpfu.kfutimetable.presentation.base.utils.BaseState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewState

open class BaseFragment<S : BaseState, VS : BaseViewState, VM : BaseViewModel<S>>(
    initialViewState: (() -> VS)?,
    viewModelProvider: BaseViewModelProvider<VM>,
) : Fragment() {

    protected val viewModel: VM = viewModelProvider.createInstance()

    protected var viewState: VS? = initialViewState?.invoke()
}