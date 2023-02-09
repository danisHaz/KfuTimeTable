package com.kpfu.kfutimetable.presentation.fqqscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kpfu.kfutimetable.databinding.FragmentAccountBinding
import com.kpfu.kfutimetable.databinding.FragmentFqqBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.fqqscreen.entities.FqqState
import com.kpfu.kfutimetable.presentation.fqqscreen.entities.FqqViewState
import com.kpfu.kfutimetable.presentation.fqqscreen.providers.FqqViewModelProvider
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FqqFragment @Inject constructor(
    private val router: Router,
    private val screenProvider: ScreenProvider
) : BaseFragment<FqqState, FqqViewState, FqqViewModel>(
    viewModelProvider = FqqViewModelProvider(),
    viewStateMapper = FqqViewStateMapper()
){

    private lateinit var binding: FragmentFqqBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFqqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners() = with(binding){

    }

    override fun render(currentViewState: FqqViewState) {

    }
}