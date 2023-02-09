package com.kpfu.kfutimetable.presentation.feedbackscreen.providers

import androidx.fragment.app.Fragment
import com.kpfu.kfutimetable.presentation.feedbackscreen.FeedbackFragment
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.Screen
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import javax.inject.Inject

class FeedbackScreen @Inject constructor(
    private val router: Router,
    private val screenProvider: ScreenProvider
) : Screen {
    override val className: Class<out Fragment>
        get() = FeedbackFragment::class.java

    override fun createNewInstance(): Fragment {
        return FeedbackFragment(router,screenProvider)
    }
}