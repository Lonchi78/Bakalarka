package com.lonchi.andrej.lonchi_bakalarka.ui.meal_planner.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lonchi.andrej.lonchi_bakalarka.LonchiBakalarkaApplication
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlanEnum
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.databinding.BottomSheetRemoveFromMealPlanBinding
import javax.inject.Inject


class RemoveFromMealPlanBottomSheet(
    private val date: String,
    private val time: MealPlanEnum,
    private val recipe: Recipe
) : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RemoveFromMealPlanViewModel
    private val vmClassToken: Class<RemoveFromMealPlanViewModel> =
        RemoveFromMealPlanViewModel::class.java

    private var binding: BottomSheetRemoveFromMealPlanBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetRemoveFromMealPlanBinding.inflate(inflater, container, false)
        (requireActivity().application as LonchiBakalarkaApplication).appComponent.inject(this)
        viewModel = createViewModel()
        setUI()
        return binding?.root
    }

    private fun createViewModel() =
        ViewModelProviders.of((activity as FragmentActivity), viewModelFactory)
            .get(this.vmClassToken)

    private fun setUI() {
        binding?.viewRemove?.setOnClickListener {
            viewModel.removeFromMealPlan(date, time, recipe)
            dismiss()
        }
    }
}