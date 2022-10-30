package com.kpfu.kfutimetable.presentation.mainscreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.databinding.FragmentCalendarBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarViewModelProvider
import com.kpfu.kfutimetable.utils.routing.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment @Inject constructor(
    calendarViewStateMapper: CalendarViewStateMapper,
    calendarViewModelProvider: CalendarViewModelProvider,
    private val router: Router,
) : BaseFragment<CalendarState, CalendarViewState, CalendarViewModel>(
    viewStateMapper = calendarViewStateMapper,
    viewModelProvider = calendarViewModelProvider
) {

    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun render(currentViewState: CalendarViewState) = with(binding) {
        sampleDayItem.render(currentViewState.dayItemViewState)
        sampleSubject.render(currentViewState.subjectViewState)
    }
}