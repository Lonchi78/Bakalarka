package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentMealPlannerBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet.FoundIngredientsBottomSheet
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.bottom_sheet.AddToMealPlanBottomSheet
import timber.log.Timber
import java.util.*

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

    private var addToMealPlanBottomSheet: AddToMealPlanBottomSheet? = null

    override fun initView() {
        (requireActivity() as? MainActivity)?.showBottomNavigation()
        binding?.iconAddBreakfast?.setOnClickListener { showAddToMealPlanBottomSheet() }
        binding?.iconAddLunch?.setOnClickListener { showAddToMealPlanBottomSheet() }
        binding?.iconAddDinner?.setOnClickListener { showAddToMealPlanBottomSheet() }
    }

    override fun bindViewModel() {
        viewModel.thisWeek.observe(viewLifecycleOwner) {
            handleThisWeek(it)
        }
    }

    override fun onDestroyView() {
        addToMealPlanBottomSheet?.dismiss()
        super.onDestroyView()
    }

    private fun handleThisWeek(week: Resource<List<MealPlannerViewModel.Companion.DayInWeek>>) {
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
        dayInWeek: MealPlannerViewModel.Companion.DayInWeek
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

    private fun fromFavouriteRecipesClick() {
    }

    private fun fromCustomRecipesClick() {
    }

    private fun fromDiscoverRecipesClick() {
    }
}