package com.kpfu.kfutimetable.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kpfu.kfutimetable.presentation.base.utils.BaseState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper

abstract class BaseFragment<S : BaseState, VS : BaseViewState, VM : BaseViewModel<S>>(
    initialViewState: (() -> VS)?,
    viewModelProvider: BaseViewModelProvider<VM>,
    private val viewStateMapper: BaseViewStateMapper<S, VS>
) : Fragment() {

    protected val viewModel: VM = viewModelProvider.createInstance()

    protected var _viewState: VS? = initialViewState?.invoke()
        get() {
            if (viewModel.state != )
            return viewStateMapper.mapToViewState(viewModel.state)
        }
    protected var viewState: VS? = _viewState

    abstract fun render(state: S)


}