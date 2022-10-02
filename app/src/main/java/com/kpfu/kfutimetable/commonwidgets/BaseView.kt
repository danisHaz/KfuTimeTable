package com.kpfu.kfutimetable.commonwidgets

interface BaseView<State> {

    fun render(state: State)
}