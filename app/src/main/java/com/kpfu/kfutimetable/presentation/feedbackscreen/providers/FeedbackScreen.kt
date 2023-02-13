package com.kpfu.kfutimetable.presentation.feedbackscreen.providers

import androidx.fragment.app.Fragment
import com.kpfu.kfutimetable.presentation.feedbackscreen.FeedbackFragment
import com.kpfu.kfutimetable.utils.routing.Screen

class FeedbackScreen : Screen {
    override val className: Class<out Fragment>
        get() = FeedbackFragment::class.java

    override fun createNewInstance(): Fragment {
        return FeedbackFragment()
    }
}