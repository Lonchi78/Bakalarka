package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.*
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentMealPlannerBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.bottom_sheet.AddToMealPlanBottomSheet
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.bottom_sheet.RemoveFromMealPlanBottomSheet
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum
import com.lonchi.andrej.lonchi_bakalarka.ui.recipes.RecipeRowsAdapter

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

    private val adapterBreakfast by lazy {
        RecipeRowsAdapter(
            context = requireContext(),
            onRecipeClick = { onRecipeClick(it) },
            onRecipeLongClick = {
                showRemoveFromMealPlanBottomSheet(
                    time = MealPlanEnum.BREAKFAST,
                    recipe = it
                )
            }
        )
    }
    private val adapterLunch by lazy {
        RecipeRowsAdapter(
            context = requireContext(),
            onRecipeClick = { onRecipeClick(it) },
            onRecipeLongClick = {
                showRemoveFromMealPlanBottomSheet(
                    time = MealPlanEnum.LUNCH,
                    recipe = it
                )
            }
        )
    }
    private val adapterDinner by lazy {
        RecipeRowsAdapter(
            context = requireContext(),
            onRecipeClick = { onRecipeClick(it) },
            onRecipeLongClick = {
                showRemoveFromMealPlanBottomSheet(
                    time = MealPlanEnum.DINNER,
                    recipe = it
                )
            }
        )
    }
    private var addToMealPlanBottomSheet: AddToMealPlanBottomSheet? = null
    private var removeFromMealPlanBottomSheet: RemoveFromMealPlanBottomSheet? = null

    override fun initView() {
        (requireActivity() as? MainActivity)?.showBottomNavigation()
        binding?.iconAddBreakfast?.setOnClickListener { showAddToMealPlanBottomSheet() }
        binding?.iconAddLunch?.setOnClickListener { showAddToMealPlanBottomSheet() }
        binding?.iconAddDinner?.setOnClickListener { showAddToMealPlanBottomSheet() }

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

    override fun onDestroyView() {
        addToMealPlanBottomSheet?.dismiss()
        removeFromMealPlanBottomSheet?.dismiss()
        super.onDestroyView()
    }

    private fun handleThisWeek(week: Resource<List<MealPlannerDay>>) {
        if (week.status is SuccessStatus && week.data != null) {
            val days = week.data
            days.getOrNull(0)?.let {
                handleDayText(binding?.textMondayNumber, binding?.layoutMonday, it)
            }
            days.getOrNull(1)?.let {
                handleDayText(binding?.textTuesdayNumber, binding?.layoutTuesday, it)
            }
            days.getOrNull(2)?.let {
                handleDayText(binding?.textWednesdayNumber, binding?.layoutWednesday, it)
            }
            days.getOrNull(3)?.let {
                handleDayText(binding?.textThursdayNumber, binding?.layoutThursday, it)
            }
            days.getOrNull(4)?.let {
                handleDayText(binding?.textFridayNumber, binding?.layoutFriday, it)
            }
            days.getOrNull(5)?.let {
                handleDayText(binding?.textSaturdayNumber, binding?.layoutSaturday, it)
            }
            days.getOrNull(6)?.let {
                handleDayText(binding?.textSundayNumber, binding?.layoutSunday, it)
            }
        } else {
            showErrorSnackbar(ErrorIdentification.Unknown(), binding?.snackbarRoot)
        }
    }

    private fun handleDayText(
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

    private fun showAddToMealPlanBottomSheet() {
        if (addToMealPlanBottomSheet?.isVisible != true) {
            addToMealPlanBottomSheet = AddToMealPlanBottomSheet(
                onFavouriteClick = ::fromFavouriteRecipesClick,
                onCustomClick = ::fromCustomRecipesClick,
                onDiscoverClick = ::fromDiscoverRecipesClick
            )
            addToMealPlanBottomSheet?.show(
                requireActivity().supportFragmentManager,
                addToMealPlanBottomSheet?.tag
            )
        }
    }

    private fun showRemoveFromMealPlanBottomSheet(time: MealPlanEnum, recipe: Recipe) {
        if (removeFromMealPlanBottomSheet?.isVisible != true) {
            viewModel.thisWeek.value?.data?.firstOrNull { it.isSelected }?.let {
                removeFromMealPlanBottomSheet = RemoveFromMealPlanBottomSheet(it.getDateId(), time, recipe)
                removeFromMealPlanBottomSheet?.show(
                    requireActivity().supportFragmentManager,
                    removeFromMealPlanBottomSheet?.tag
                )
            }
        }
    }

    private fun fromFavouriteRecipesClick() {
        findNavController().navigate(
            MealPlannerFragmentDirections.actionHomeFragmentToFavouritesFragment()
        )
    }

    private fun fromCustomRecipesClick() {
        findNavController().navigate(
            MealPlannerFragmentDirections.actionHomeFragmentToOwnRecipesFragment()
        )
    }

    private fun fromDiscoverRecipesClick() {
        findNavController().navigate(
            MealPlannerFragmentDirections.actionHomeFragmentToDiscoverListFragment()
        )
    }

    private fun onRecipeClick(recipe: RecipeItem) {
        //  TODO - every recipe is REST, because idType is not stored
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