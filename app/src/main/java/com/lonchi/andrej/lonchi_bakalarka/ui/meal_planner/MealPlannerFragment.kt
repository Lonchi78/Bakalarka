package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner

import android.view.View
import com.google.android.material.chip.Chip
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentMealPlannerBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity

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

    override fun initView() {
        (requireActivity() as? MainActivity)?.showBottomNavigation()
        binding?.labelMealPlanner?.setOnClickListener {
            val chip = layoutInflater.inflate(
                R.layout.chip_single_choice,
                binding?.chipGroup,
                false
            ) as Chip
            chip.text = (0..100).random().toString()
            binding?.chipGroup?.addView(chip)
        }
    }

    override fun bindViewModel() = Unit
}