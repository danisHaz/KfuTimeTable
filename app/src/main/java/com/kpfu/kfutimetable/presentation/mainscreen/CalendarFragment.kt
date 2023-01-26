package com.kpfu.kfutimetable.presentation.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.commonwidgets.TopSheetDialog.TopSheetDialog
import com.kpfu.kfutimetable.databinding.FragmentCalendarBinding
import com.kpfu.kfutimetable.presentation.base.BaseFragment
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarState
import com.kpfu.kfutimetable.presentation.mainscreen.entities.CalendarViewState
import com.kpfu.kfutimetable.presentation.mainscreen.providers.CalendarViewModelProvider
import com.kpfu.kfutimetable.presentation.mainscreen.utils.MonthCarousel
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
    private val monthList = ArrayList<String>().apply {
        add("September")
        add("October")
        add("November")
    }

    private var monthCarousel: MonthCarousel? = null
    private var menuDialog: TopSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        menuDialog = TopSheetDialog(requireContext(), R.style.TopSheet).apply {
            window?.attributes?.windowAnimations = R.style.TopSheet_DialogAnimation
            setContentView(R.layout.layout_top_slidable_menu)
        }

        binding.dayItemCarousel.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        monthCarousel = MonthCarousel(monthList, binding.monthList)
        setListeners()
    }

    override fun render(currentViewState: CalendarViewState) = with(binding) {
        dayItemCarousel.render(currentViewState.dayItemCarouselState)
        timetableView.render(currentViewState.subjectViewState)
    }

    private fun setListeners() = with(binding) {
        nextButton.setOnClickListener {
            monthCarousel?.nextMonth()
        }

        prevButton.setOnClickListener {
            monthCarousel?.prevMonth()
        }

        this.toolbar.menu.setOnClickListener {
            if (menuDialog?.isShowing == true) {
                menuDialog?.hide()
            } else {
                menuDialog?.show()
            }
        }
    }
}