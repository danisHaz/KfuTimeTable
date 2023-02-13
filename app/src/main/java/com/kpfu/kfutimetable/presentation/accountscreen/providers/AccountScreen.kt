package com.kpfu.kfutimetable.presentation.accountscreen.providers

import androidx.fragment.app.Fragment
import com.kpfu.kfutimetable.presentation.accountscreen.AccountFragment
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.Screen
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import javax.inject.Inject

class AccountScreen @Inject constructor(
    private val router: Router,
    private val screenProvider: ScreenProvider
) : Screen {
    override val className: Class<out Fragment>
        get() = AccountFragment::class.java

    override fun createNewInstance(): Fragment {
        return AccountFragment(router, screenProvider)
    }
}