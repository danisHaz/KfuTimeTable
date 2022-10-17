package com.kpfu.kfutimetable.utils.routing

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import javax.inject.Inject

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