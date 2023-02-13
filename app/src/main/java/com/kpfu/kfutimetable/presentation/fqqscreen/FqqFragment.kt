package com.kpfu.kfutimetable.presentation.fqqscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kpfu.kfutimetable.databinding.FragmentFqqBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.fqqscreen.entities.FqqState
import com.kpfu.kfutimetable.presentation.fqqscreen.entities.FqqViewState
import com.kpfu.kfutimetable.presentation.fqqscreen.providers.FqqViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FqqFragment : BaseFragment<FqqState, FqqViewState, FqqViewModel>(
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

    override fun render(currentViewState: FqqViewState) {}
}