package com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lonchi.andrej.lonchi_bakalarka.LonchiBakalarkaApplication
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.adapter.FoundIngredientsAdapter
import kotlinx.android.synthetic.main.bottom_sheet_found_ingredients.view.*
import javax.inject.Inject


class FoundIngredientsBottomSheet(
    private val onConfirmClick: (List<ImageLabelingItem>) -> Unit
) : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: FoundIngredientsViewModel
    private val vmClassToken: Class<FoundIngredientsViewModel> =
        FoundIngredientsViewModel::class.java

    private var rootView: View? = null
    private val adapter by lazy {
        FoundIngredientsAdapter(requireContext()) {
            onSelectedIngredientsChange(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.bottom_sheet_found_ingredients, container, false)
        (requireActivity().application as LonchiBakalarkaApplication).appComponent.inject(this)
        viewModel = createViewModel()
        setUI()
        return rootView
    }

    private fun createViewModel() =
        ViewModelProviders.of((activity as FragmentActivity), viewModelFactory)
            .get(this.vmClassToken)

    private fun setUI() {
        rootView?.buttonAddIngredients?.isEnabled = false

        rootView?.recyclerIngredients?.adapter = adapter
        rootView?.recyclerIngredients?.layoutManager = LinearLayoutManager(requireContext())
        /*viewModel.foundIngredients.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }       */
        viewModel.imageLabelingState.observe(viewLifecycleOwner) {
            if (it.status is SuccessStatus && it.data != null) {
                adapter.submitList(it.data.items)
            }
        }

        rootView?.buttonAddIngredients?.setOnClickListener {
            onConfirmClick(adapter.getSelectedIngredients())
            dismiss()
        }
    }

    private fun onSelectedIngredientsChange(selectedIngredients: List<ImageLabelingItem>) {
        rootView?.buttonAddIngredients?.isEnabled = selectedIngredients.isNotEmpty()
        if (selectedIngredients.size > 1) {
            rootView?.buttonAddIngredients?.text =
                getString(R.string.found_ingredients_button_multi)
        } else {
            rootView?.buttonAddIngredients?.text =
                getString(R.string.found_ingredients_button_single)
        }
    }
}