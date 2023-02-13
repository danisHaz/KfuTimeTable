package com.kpfu.kfutimetable.presentation.feedbackscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kpfu.kfutimetable.databinding.FragmentFeedbackBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackState
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackViewState
import com.kpfu.kfutimetable.presentation.feedbackscreen.providers.FeedbackViewModelProvider

class FeedbackFragment : BaseFragment<FeedbackState,FeedbackViewState, FeedbackViewModel>(
    viewModelProvider = FeedbackViewModelProvider(),
    viewStateMapper = FeedbackViewStateMapper()
) {
    private lateinit var binding: FragmentFeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun render(currentViewState: FeedbackViewState) {}
}