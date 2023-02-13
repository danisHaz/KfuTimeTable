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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calendar.*
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
    private val monthList = ArrayList<String>().apply {
        add("September")
        add("October")
        add("November")
    }

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
        binding.dayItemCarousel.onItemClick = {
            viewModel.getLessonsByDay(Integer.parseInt(it.date))
        }
        binding.dayItemCarousel.initialize()
        setListeners()
        setObservers()
        viewModel.getCurrentMonthsList()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopJob()
    }

    override fun render(currentViewState: CalendarViewState) = with(binding) {
        timetableView.render(currentViewState.subjectViewState[0])
    }

    private fun setListeners() = with(binding) {
        nextButton.setOnClickListener {
            monthCarousel?.nextMonth()
        }

        prevButton.setOnClickListener {
            monthCarousel?.prevMonth()
        }
    }

    private fun setObservers() {
        viewModel.monthListData.observe(this.viewLifecycleOwner) {
            monthCarousel = MonthCarousel(it, binding.monthList)
            viewModel.initDayItemCarousel(it)
        }
        viewModel.dayItemCarouselData.observe(this.viewLifecycleOwner) {
            val dayItemStateList = mutableListOf<DayItemView.State>().apply {
                for (dayWrapper in it) {
                    add(
                        DayItemView.State(
                            date = dayWrapper.date.dayOfMonth.toString(),
                            dayOfWeek = dayWrapper.date.dayOfWeek.toString(),
                            isChecked = dayWrapper.param
                        )
                    )
                }
            }
            binding.dayItemCarousel.render(dayItemStateList)
        }
    }

}