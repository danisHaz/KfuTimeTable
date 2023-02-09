package com.kpfu.kfutimetable.presentation.fqqscreen

import com.kpfu.kfutimetable.presentation.base.utils.BaseViewStateMapper
import com.kpfu.kfutimetable.presentation.fqqscreen.entities.FqqState
import com.kpfu.kfutimetable.presentation.fqqscreen.entities.FqqViewState

class FqqViewStateMapper : BaseViewStateMapper<FqqState,FqqViewState>{
    override fun mapToViewState(state: FqqState): FqqViewState {
        return FqqViewState()
    }
}