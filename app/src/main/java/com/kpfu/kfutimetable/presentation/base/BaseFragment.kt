package com.kpfu.kfutimetable.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.kpfu.kfutimetable.presentation.base.utils.BaseState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper

abstract class BaseFragment<S : BaseState, VS : BaseViewState, VM : BaseViewModel<S>>(
    initialViewState: (() -> VS)?,
    private val viewModelProvider: BaseViewModelProvider<VM>,
    private val viewStateMapper: BaseViewStateMapper<S, VS>
) : Fragment() {

    protected lateinit var viewModel: VM

    protected var viewState: VS? = initialViewState?.invoke()
        get() = viewStateMapper.mapToViewState(viewModel.state)

    fun onAttachedToOwner(owner: ViewModelStoreOwner) {
        viewModel = viewModelProvider.createInstance(owner)
    }

    abstract fun render(state: S)
}