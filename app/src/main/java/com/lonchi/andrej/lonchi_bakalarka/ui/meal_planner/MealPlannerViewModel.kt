package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.combineLatestLiveData
import com.lonchi.andrej.lonchi_bakalarka.ui.allergens.adapter.AddAllergensObject
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MealPlannerViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        const val DAYS_IN_WEEK = 7

        data class DayInWeek(
            val day: Int,
            val month: Int,
            val year: Int,
            val isToday: Boolean = false,
            val isSelected: Boolean = false
        ) {
            fun isSameDay(otherDay: DayInWeek): Boolean {
                return this.day == otherDay.day &&
                        this.month == otherDay.month &&
                        this.year == otherDay.year
            }
        }
    }

    private val todayData: MutableLiveData<DayInWeek> = MutableLiveData<DayInWeek>().apply {
        postValue(null)
    }
    private val selectedDayData: MutableLiveData<DayInWeek> =
        MutableLiveData<DayInWeek>().apply {
            postValue(null)
        }
    private val thisWeekData: MutableLiveData<List<DayInWeek>> =
        MutableLiveData<List<DayInWeek>>().apply {
            postValue(null)
        }
    val thisWeek: LiveData<Resource<List<DayInWeek>>> = Transformations.map(
        combineLatestLiveData(
            todayData,
            selectedDayData,
            thisWeekData
        )
    ) {
        val todayData = it.first
        val selectedDayData = it.second
        val weekData = it.third
        if (todayData == null || selectedDayData == null || weekData.isNullOrEmpty()) {
            return@map Resource.notStarted()
        }

        val tmp = mutableListOf<DayInWeek>()
        weekData.forEach { dayInWeek ->
            tmp.add(
                DayInWeek(
                    day = dayInWeek.day,
                    month = dayInWeek.month,
                    year = dayInWeek.year,
                    isToday = dayInWeek.isSameDay(todayData),
                    isSelected = dayInWeek.isSameDay(selectedDayData)
                )
            )
        }
        Resource.success(tmp)
    }

    init {
        setupThisWeek()
    }

    fun changeSelectedDay(selectedDay: DayInWeek) {
        selectedDayData.postValue(selectedDay)
    }

    private fun setupThisWeek() {
        val todayCalendar = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        //  Parse days of the week
        val days = mutableListOf<DayInWeek>()
        for (i in 0 until DAYS_IN_WEEK) {
            days.add(
                DayInWeek(
                    day = calendar.get(Calendar.DAY_OF_MONTH),
                    month = calendar.get(Calendar.MONTH) + 1,
                    year = calendar.get(Calendar.YEAR)
                )
            )
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        //  Fill live data
        val tmp = DayInWeek(
            day = todayCalendar.get(Calendar.DAY_OF_MONTH),
            month = todayCalendar.get(Calendar.MONTH) + 1,
            year = todayCalendar.get(Calendar.YEAR),
            isToday = true
        )
        todayData.postValue(tmp)
        selectedDayData.postValue(tmp)
        thisWeekData.postValue(days)
    }

}