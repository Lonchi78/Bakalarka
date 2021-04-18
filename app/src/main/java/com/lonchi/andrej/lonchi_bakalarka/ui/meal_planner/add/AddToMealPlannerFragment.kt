package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.add

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlan
import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlanEnum
import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlannerDay
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentAddToMealPlannerBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.MealPlannerFragmentDirections
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum
import com.lonchi.andrej.lonchi_bakalarka.ui.recipes.RecipeRowsAdapter


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class AddToMealPlannerFragment : BaseFragment<AddToMealPlannerViewModel, FragmentAddToMealPlannerBinding>() {
    companion object {
        fun newInstance() = AddToMealPlannerFragment()
    }

    override val layoutId: Int = R.layout.fragment_add_to_meal_planner
    override val vmClassToken: Class<AddToMealPlannerViewModel> = AddToMealPlannerViewModel::class.java
    override val bindingInflater: (View) -> FragmentAddToMealPlannerBinding = { FragmentAddToMealPlannerBinding.bind(it) }

    private val adapterBreakfast by lazy {
        RecipeRowsAdapter(
            context = requireContext(),
            onRecipeClick = { onRecipeClick(it) }
        )
    }
    private val adapterLunch by lazy {
        RecipeRowsAdapter(
            context = requireContext(),
            onRecipeClick = { onRecipeClick(it) }
        )
    }
    private val adapterDinner by lazy {
        RecipeRowsAdapter(
            context = requireContext(),
            onRecipeClick = { onRecipeClick(it) }
        )
    }

    override fun initView() {
        (requireActivity() as? MainActivity)?.hideBottomNavigation()
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.iconAddBreakfast?.setOnClickListener { viewModel.saveToMealPlan(MealPlanEnum.BREAKFAST) }
        binding?.iconAddLunch?.setOnClickListener { viewModel.saveToMealPlan(MealPlanEnum.LUNCH) }
        binding?.iconAddDinner?.setOnClickListener { viewModel.saveToMealPlan(MealPlanEnum.DINNER) }

        binding?.recyclerBreakfast?.adapter = adapterBreakfast
        binding?.recyclerBreakfast?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.recyclerLunch?.adapter = adapterLunch
        binding?.recyclerLunch?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.recyclerDinner?.adapter = adapterDinner
        binding?.recyclerDinner?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        viewModel.thisWeek.observe(viewLifecycleOwner) {
            handleThisWeek(it)
        }
        viewModel.selectedMealPlan.observe(viewLifecycleOwner) {
            handleMealPlan(it)
        }
    }

    private fun handleThisWeek(week: Resource<List<MealPlannerDay>>) {
        if (week.status is SuccessStatus && week.data != null) {
            val days = week.data
            days.getOrNull(0)?.let {
                handleDay(binding?.textMondayNumber, binding?.layoutMonday, it)
            }
            days.getOrNull(1)?.let {
                handleDay(binding?.textTuesdayNumber, binding?.layoutTuesday, it)
            }
            days.getOrNull(2)?.let {
                handleDay(binding?.textWednesdayNumber, binding?.layoutWednesday, it)
            }
            days.getOrNull(3)?.let {
                handleDay(binding?.textThursdayNumber, binding?.layoutThursday, it)
            }
            days.getOrNull(4)?.let {
                handleDay(binding?.textFridayNumber, binding?.layoutFriday, it)
            }
            days.getOrNull(5)?.let {
                handleDay(binding?.textSaturdayNumber, binding?.layoutSaturday, it)
            }
            days.getOrNull(6)?.let {
                handleDay(binding?.textSundayNumber, binding?.layoutSunday, it)
            }
        } else {
            showErrorSnackbar(ErrorIdentification.Unknown(), binding?.snackbarRoot)
        }
    }

    private fun handleDay(
        textView: TextView?,
        rootLayout: ConstraintLayout?,
        dayInWeek: MealPlannerDay
    ) {
        textView?.text = dayInWeek.day.toString()
        rootLayout?.setOnClickListener { viewModel.changeSelectedDay(dayInWeek) }
        when {
            dayInWeek.isToday && dayInWeek.isSelected -> {
                textView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textView?.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.green500)
                )
            }
            dayInWeek.isToday && !dayInWeek.isSelected -> {
                textView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray900))
                textView?.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.gray200)
                )
            }
            dayInWeek.isSelected -> {
                textView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textView?.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.green500)
                )
            }
            else -> {
                textView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray900))
                textView?.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.transparent)
                )
            }
        }
    }

    private fun handleMealPlan(mealPlan: MealPlan?) {
        adapterBreakfast.submitList(mealPlan?.breakfast ?: listOf())
        adapterLunch.submitList(mealPlan?.lunch ?: listOf())
        adapterDinner.submitList(mealPlan?.dinner ?: listOf())
    }

    private fun onRecipeClick(recipe: RecipeItem) {
        when (recipe.getRecipeType()) {
            RecipeIdTypeEnum.FAVOURITE_RECIPE -> {
                findNavController().navigate(
                    MealPlannerFragmentDirections.actionGlobalRecipeDetailFragment(
                        recipeId = recipe.getId(),
                        idType = RecipeIdTypeEnum.FAVOURITE_RECIPE
                    )
                )
            }
            RecipeIdTypeEnum.OWN_RECIPE -> {
                findNavController().navigate(
                    MealPlannerFragmentDirections.actionGlobalRecipeDetailCustomFragment(
                        recipeId = recipe.getId(),
                        idType = RecipeIdTypeEnum.OWN_RECIPE
                    )
                )
            }
            RecipeIdTypeEnum.REST -> {
                findNavController().navigate(
                    MealPlannerFragmentDirections.actionGlobalRecipeDetailFragment(
                        recipeId = recipe.getId(),
                        idType = RecipeIdTypeEnum.REST
                    )
                )
            }
        }
    }
}