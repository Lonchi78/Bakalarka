package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentHomeBinding
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentMealPlannerBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MealPlannerFragment : BaseFragment<MealPlannerViewModel, FragmentMealPlannerBinding>() {
    companion object {
        fun newInstance() = MealPlannerFragment()
    }

    override val layoutId: Int = R.layout.fragment_meal_planner
    override val vmClassToken: Class<MealPlannerViewModel> = MealPlannerViewModel::class.java
    override val bindingInflater: (View) -> FragmentMealPlannerBinding = { FragmentMealPlannerBinding.bind(it) }

    override fun initView() = Unit

    override fun bindViewModel() = Unit
}