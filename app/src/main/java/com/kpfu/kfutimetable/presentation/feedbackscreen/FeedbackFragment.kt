package com.kpfu.kfutimetable.presentation.feedbackscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.databinding.FragmentFeedbackBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackState
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackViewState
import com.kpfu.kfutimetable.presentation.feedbackscreen.providers.FeedbackViewModelProvider

class FeedbackFragment : BaseFragment<FeedbackState, FeedbackViewState, FeedbackViewModel>(
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
    }


    override fun render(currentViewState: FeedbackViewState) {}

    private fun setListeners() {
        binding.button.setOnClickListener { view ->
            binding.editText1.text.toString().takeIf { it.isNotEmpty() }?.let { report ->
                viewModel.postNewReport(report)
            }
        }
    }

    private fun setObservers() {
        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                setSnackbar(
                    binding.root,
                    resources.getString(R.string.please_try_again),
                    action = null,
                    toPerform = null
                )
            }
        }

        viewModel.isSent.observe(viewLifecycleOwner) {
            if (it) {
                setSnackbar(
                    binding.root,
                    resources.getString(R.string.report_successful),
                    action = null,
                    toPerform = null
                )
            }
        }
    }
}