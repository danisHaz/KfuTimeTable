package com.kpfu.kfutimetable.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kpfu.kfutimetable.presentation.base.utils.BaseState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewState
import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import com.kpfu.kfutimetable.utils.launchWhenStarted
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<S : BaseState, VS : BaseViewState, VM : BaseViewModel<S, VS>>(
    private val viewModelProvider: BaseViewModelProvider<VM>,
    private val viewStateMapper: BaseViewStateMapper<S, VS>
) : Fragment() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::viewModel.isInitialized) {
            viewModel = viewModelProvider.createInstance(this)
        }
        viewModel.viewStateFlow
            .onEach { viewState -> render(viewState) }
            .launchWhenStarted(lifecycleScope)
    }

    protected val viewState: VS
        get() = viewModel.viewStateFlow.value

    abstract fun render(currentViewState: VS)
}