package com.kpfu.kfutimetable.utils.routing

import androidx.fragment.app.Fragment
import com.kpfu.kfutimetable.presentation.accountscreen.providers.AccountScreen
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarScreen
import com.kpfu.kfutimetable.presentation.signinscreen.providers.SignInScreen
import javax.inject.Inject
import javax.inject.Provider

interface Screen {

    val className: Class<out Fragment>

    fun createNewInstance(): Fragment
}

class ScreenProvider @Inject constructor(
    private val calendarScreen: Provider<CalendarScreen>,
    private val signInScreen: Provider<SignInScreen>,
    private val accountScreen: Provider<AccountScreen>

) {

    fun get(type: ScreenType): Screen = when(type) {
        ScreenType.CalendarFragment -> calendarScreen.get()
        ScreenType.SignInFragment -> signInScreen.get()
        ScreenType.AccountFragment -> accountScreen.get()
    }

    enum class ScreenType {
        CalendarFragment,
        SignInFragment,
        AccountFragment
    }
}