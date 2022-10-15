package com.kpfu.kfutimetable.utils

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.kpfu.kfutimetable.utils.routing.Screen
import javax.inject.Inject

class Router @Inject constructor(
    private val fragmentManager: FragmentManager,
    private val fragmentContainerId: Int,
) {

    fun navigate(screen: Screen, addToBackStack: Boolean = true) {
        fragmentManager.commit {
            replace(fragmentContainerId)
            if (addToBackStack)
                addToBackStack(null)
        }
    }
}
