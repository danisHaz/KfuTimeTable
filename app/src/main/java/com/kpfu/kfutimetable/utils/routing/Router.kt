package com.kpfu.kfutimetable.utils.routing

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

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

    fun navigate(screen: Screen, addToBackStack: Boolean = true) {
        fragmentManager.commit {
            replace(fragmentContainerId, screen.createNewInstance())
            if (addToBackStack)
                addToBackStack(null)
        }
    }
}