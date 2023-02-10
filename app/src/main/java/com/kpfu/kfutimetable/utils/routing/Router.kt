package com.kpfu.kfutimetable.utils.routing

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.kpfu.kfutimetable.presentation.base.utils.BaseApplication

object RouteManager {

    private var routerInstance: Router? = null

    val router: Router?
        get() = routerInstance

    fun initializeRouter(
        fragmentManager: FragmentManager,
        fragmentContainerId: Int,
    ) {
        if (routerInstance == null) {
            routerInstance = Router(fragmentManager, fragmentContainerId)
        } else {
            Log.e(this::class.java.simpleName, "Router is already initialized")
        }
    }

    fun removeRouter() {
        routerInstance = null
    }
}

class Router(
    private val fragmentManager: FragmentManager,
    private val fragmentContainerId: Int,
) {

    private var wasBackstackCleared = false

    fun clearBackStack() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        wasBackstackCleared = true
    }

    fun navigate(
        screen: Screen,
        addToBackStack: Boolean = true,
        executePendingTransactions: Boolean = false
    ) {
        fragmentManager.commit {
            replace(fragmentContainerId, screen.createNewInstance())
            if (addToBackStack)
                addToBackStack(null)
        }
        if (executePendingTransactions) {
            fragmentManager.executePendingTransactions()
        }
    }
}