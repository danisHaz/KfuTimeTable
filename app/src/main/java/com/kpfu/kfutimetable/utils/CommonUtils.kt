package com.kpfu.kfutimetable.utils

data class FragmentEntity<T>(
    val tag: String,
    val className: Class<T>,
    val data:
)

data class Save

interface BaseRouter {

    fun navigateTo()

    fun popFromBackstack(): FragmentEntity
}

class RouterImpl : BaseRouter {
    override fun navigateTo() {

    }

    override fun popFromBackstack(): FragmentEntity {

    }

}