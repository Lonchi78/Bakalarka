package com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lonchi.andrej.lonchi_bakalarka.LonchiBakalarkaApplication
import com.lonchi.andrej.lonchi_bakalarka.R
import javax.inject.Inject


class FoundIngredientsBottomSheet(
    private val showSettings: () -> Unit
) : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: FoundIngredientsViewModel
    private val vmClassToken: Class<FoundIngredientsViewModel> = FoundIngredientsViewModel::class.java


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
        val buttonAddIngredients = view.findViewById<Button>(R.id.buttonAddIngredients)
        buttonAddIngredients.setOnClickListener {
            dismiss()
        }
    }
}