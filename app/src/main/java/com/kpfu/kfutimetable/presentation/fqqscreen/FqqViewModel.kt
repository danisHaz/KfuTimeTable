package com.kpfu.kfutimetable.presentation.fqqscreen

import com.kpfu.kfutimetable.presentation.base.BaseViewModel
import com.kpfu.kfutimetable.presentation.fqqscreen.entities.FqqState
import com.kpfu.kfutimetable.presentation.fqqscreen.entities.FqqViewState

class FqqViewModel : BaseViewModel<FqqState,FqqViewState>(
    initialState = { FqqState() },
    viewStateMapper = FqqViewStateMapper()
)