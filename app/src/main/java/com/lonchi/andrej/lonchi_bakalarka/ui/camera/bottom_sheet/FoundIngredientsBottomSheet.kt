package com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lonchi.andrej.lonchi_bakalarka.LonchiBakalarkaApplication
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.adapter.FoundIngredientsAdapter
import kotlinx.android.synthetic.main.bottom_sheet_found_ingredients.view.*
import timber.log.Timber
import javax.inject.Inject


class FoundIngredientsBottomSheet(
    private val onConfirmClick: () -> Unit
) : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: FoundIngredientsViewModel
    private val vmClassToken: Class<FoundIngredientsViewModel> = FoundIngredientsViewModel::class.java

    private val adapter by lazy { FoundIngredientsAdapter(requireContext()) { onIngredientClick(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_found_ingredients, container, false)
        (requireActivity().application as LonchiBakalarkaApplication).appComponent.inject(this)
        viewModel = createViewModel()
        setUI(view)
        return view
    }

    private fun createViewModel() = ViewModelProviders.of((activity as FragmentActivity), viewModelFactory).get(this.vmClassToken)

    private fun setUI(view: View) {
        view.recyclerIngredients?.adapter = adapter
        view.recyclerIngredients?.layoutManager = LinearLayoutManager(requireContext())
        Timber.d("setUI: vm is null? = ${viewModel == null}")
        viewModel.foundIngredients.observe(viewLifecycleOwner) {
            Timber.d("setUI: observe, size = ${it.size}")
            adapter.submitList(it)
        }

        view.buttonAddIngredients.setOnClickListener {
            onConfirmClick()
            dismiss()
        }
    }

    private fun onIngredientClick(ingredient: Ingredient) {
        Timber.d("onIngredientClick: $ingredient")
    }
}