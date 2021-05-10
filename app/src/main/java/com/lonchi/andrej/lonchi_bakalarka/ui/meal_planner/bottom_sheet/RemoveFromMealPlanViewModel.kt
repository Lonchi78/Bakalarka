package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.bottom_sheet

import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlanEnum
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.repository.MealPlanRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class RemoveFromMealPlanViewModel @Inject constructor(
    private val mealPlanRepository: MealPlanRepository
) : BaseViewModel() {
    fun removeFromMealPlan(date: String, time: MealPlanEnum, recipe: Recipe) {
        mealPlanRepository.removeFromMealPlan(date, time, recipe)
    }
}