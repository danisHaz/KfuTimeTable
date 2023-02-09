package com.kpfu.kfutimetable.presentation.feedbackscreen

import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackState
import com.kpfu.kfutimetable.presentation.feedbackscreen.entities.FeedbackViewState

class FeedbackViewStateMapper : BaseViewStateMapper<FeedbackState, FeedbackViewState>{
    override fun mapToViewState(state: FeedbackState): FeedbackViewState {
        return FeedbackViewState()
    }
}