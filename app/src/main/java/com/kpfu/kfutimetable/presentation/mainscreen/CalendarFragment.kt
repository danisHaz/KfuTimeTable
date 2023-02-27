package com.kpfu.kfutimetable.presentation.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpfu.kfutimetable.commonwidgets.DayItemView
import com.kpfu.kfutimetable.databinding.FragmentCalendarBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarViewModelProvider
import com.kpfu.kfutimetable.presentation.mainscreen.utils.MonthCarousel
import com.kpfu.kfutimetable.utils.routing.Router
import com.kpfu.kfutimetable.utils.routing.ScreenProvider
import com.kpfu.kfutimetable.utils.toString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.two_icons.*
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment @Inject constructor(
    calendarViewStateMapper: CalendarViewStateMapper,
    calendarViewModelProvider: CalendarViewModelProvider,
    private val router: Router,
    private val screenProvider: ScreenProvider
) : BaseFragment<CalendarState, CalendarViewState, CalendarViewModel>(
    viewStateMapper = calendarViewStateMapper,
    viewModelProvider = calendarViewModelProvider
) {

    private lateinit var binding: FragmentCalendarBinding
    private var monthCarousel: MonthCarousel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.dayItemCarousel.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        setListeners()
        setObservers()
        binding.dayItemCarousel.initialize()
        viewModel.getCurrentMonthsList()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopJob()
        monthCarousel = null
    }

    override fun render(currentViewState: CalendarViewState) = with(binding) {
        timetableView.render(currentViewState.subjectViewState[0])
    }

    private fun setListeners() = with(binding) {
        nextButton.setOnClickListener {
            monthCarousel?.nextMonth()
            Log.e("kek", monthCarousel?.currentMonth.toString())
        }
        prevButton.setOnClickListener {
            monthCarousel?.prevMonth()
        }
        dayItemCarousel.onItemClick = {
            monthCarousel?.currentMonth?.let { currentMonth ->
                viewModel.getLessonsOnDay(Integer.parseInt(it.date), currentMonth)
            } ?: Log.e(this::class.java.name, "Month carousel is null")
        }
        feedbackPageButton.setOnClickListener {
            router.navigate(
                screenProvider.get(ScreenProvider.ScreenType.FeedbackFragment)
            )
        }
        faqPageButton.setOnClickListener {
            router.navigate(
                screenProvider.get(ScreenProvider.ScreenType.FqqFragment)
            )
        }
    }

    private fun setObservers() {
        viewModel.monthListData.observe(this.viewLifecycleOwner) {
            monthCarousel = MonthCarousel(context, it, binding.monthList)
            monthCarousel?.onMonthChangeListener = { month ->
                viewModel.updateDayItemCarousel(month)
            }
            viewModel.initDayItemCarousel(it)
        }
        viewModel.dayItemCarouselData.observe(this.viewLifecycleOwner) {
            val dayItemStateList = mutableListOf<DayItemView.State>().apply {
                for (dayWrapper in it) {
                    add(
                        DayItemView.State(
                            date = dayWrapper.date.dayOfMonth.toString(),
                            dayOfWeek = dayWrapper.date.dayOfWeek.toString(requireContext()),
                            isChecked = dayWrapper.param
                        )
                    )
                }
            }
            binding.dayItemCarousel.render(dayItemStateList)
        }
    }
}