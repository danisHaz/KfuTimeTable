package com.kpfu.kfutimetable.presentation.feedbackscreen.providers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.kpfu.kfutimetable.presentation.base.BaseViewModelProvider
import com.kpfu.kfutimetable.presentation.feedbackscreen.FeedbackViewModel

class FeedbackViewModelProvider : BaseViewModelProvider<FeedbackViewModel>{
    override fun createInstance(owner: ViewModelStoreOwner): FeedbackViewModel {
        return ViewModelProvider(owner).get(FeedbackViewModel::class.java)
    }
}