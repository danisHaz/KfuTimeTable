package com.kpfu.kfutimetable.presentation.signinscreen.providers

import androidx.fragment.app.Fragment
import com.kpfu.kfutimetable.presentation.mainscreen.CalendarFragment
import com.kpfu.kfutimetable.presentation.signinscreen.SignInFragment
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.Screen
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import javax.inject.Inject

class SignInScreen @Inject constructor(
    private val router: Router,
    private val screenProvider: ScreenProvider
) : Screen {
    override val className: Class<out Fragment>
        get() = SignInFragment::class.java

    override fun createNewInstance(): Fragment {
        return SignInFragment(router, screenProvider)
    }
}