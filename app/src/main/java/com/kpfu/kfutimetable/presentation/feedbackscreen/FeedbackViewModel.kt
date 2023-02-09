package com.kpfu.kfutimetable.presentation.feedbackscreen

import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackState
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackViewState

class FeedbackViewModel : BaseViewModel<FeedbackState, FeedbackViewState>(
    initialState = { FeedbackState() },
    viewStateMapper = FeedbackViewStateMapper()
)
