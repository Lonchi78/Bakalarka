package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlanEnum
import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlannerDay
import com.lonchi.andrej.lonchi_bakalarka.data.repository.MealPlanRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.combineLatestLiveData
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class AddToMealPlannerViewModel @Inject constructor(
    private val mealPlannerRepository: MealPlanRepository
) : BaseViewModel() {

    companion object {
        const val DAYS_IN_WEEK = 7
    }

    private val todayData: MutableLiveData<MealPlannerDay> = MutableLiveData<MealPlannerDay>().apply {
        postValue(null)
    }
    private val selectedDayData: MutableLiveData<MealPlannerDay> =
        MutableLiveData<MealPlannerDay>().apply {
            postValue(null)
        }
    private val thisWeekData: MutableLiveData<List<MealPlannerDay>> =
        MutableLiveData<List<MealPlannerDay>>().apply {
            postValue(null)
        }
    val thisWeek: LiveData<Resource<List<MealPlannerDay>>> = Transformations.map(
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

        val tmp = mutableListOf<MealPlannerDay>()
        weekData.forEach { mealPlannerDay ->
            tmp.add(
                MealPlannerDay(
                    day = mealPlannerDay.day,
                    month = mealPlannerDay.month,
                    year = mealPlannerDay.year,
                    isToday = mealPlannerDay.isSameDay(todayData),
                    isSelected = mealPlannerDay.isSameDay(selectedDayData)
                )
            )
        }
        Resource.success(tmp)
    }

    init {
        setupThisWeek()
    }

    fun changeSelectedDay(selectedDay: MealPlannerDay) {
        selectedDayData.postValue(selectedDay)
    }

    private fun setupThisWeek() {
        val todayCalendar = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        //  Parse days of the week
        val days = mutableListOf<MealPlannerDay>()
        for (i in 0 until DAYS_IN_WEEK) {
            days.add(
                MealPlannerDay(
                    day = calendar.get(Calendar.DAY_OF_MONTH),
                    month = calendar.get(Calendar.MONTH) + 1,
                    year = calendar.get(Calendar.YEAR)
                )
            )
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        //  Fill live data
        val tmp = MealPlannerDay(
            day = todayCalendar.get(Calendar.DAY_OF_MONTH),
            month = todayCalendar.get(Calendar.MONTH) + 1,
            year = todayCalendar.get(Calendar.YEAR),
            isToday = true
        )
        todayData.postValue(tmp)
        selectedDayData.postValue(tmp)
        thisWeekData.postValue(days)
    }

    fun saveToMealPlan(time: MealPlanEnum) {
        val selectedDate = selectedDayData.value?.getDateId() ?: MealPlannerDay(
            day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            month = Calendar.getInstance().get(Calendar.MONTH) + 1,
            year = Calendar.getInstance().get(Calendar.YEAR)
        ).getDateId()

        mealPlannerRepository.saveToMealPlan(
            date = selectedDate,
            time = time
        )
    }

}