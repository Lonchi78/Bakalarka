package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner

import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MealPlannerFragment : BaseFragment<MealPlannerViewModel>() {
    companion object {
        fun newInstance() = MealPlannerFragment()
    }

    override val layoutId: Int = R.layout.fragment_meal_planner
    override val vmClassToken: Class<MealPlannerViewModel> = MealPlannerViewModel::class.java

    override fun initView() = Unit

    override fun bindViewModel() = Unit
}