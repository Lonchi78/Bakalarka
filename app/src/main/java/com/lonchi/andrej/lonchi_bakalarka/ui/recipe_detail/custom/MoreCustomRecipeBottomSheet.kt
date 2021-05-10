package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.custom

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
import com.lonchi.andrej.lonchi_bakalarka.databinding.BottomSheetMoreCustomRecipeBinding
import javax.inject.Inject


class MoreCustomRecipeBottomSheet(
    private val deleteRecipe: () -> Unit
) : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MoreCustomRecipeViewModel
    private val vmClassToken: Class<MoreCustomRecipeViewModel> =
        MoreCustomRecipeViewModel::class.java

    private var binding: BottomSheetMoreCustomRecipeBinding? = null

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
        binding = BottomSheetMoreCustomRecipeBinding.inflate(inflater, container, false)
        (requireActivity().application as LonchiBakalarkaApplication).appComponent.inject(this)
        viewModel = createViewModel()
        setUI()
        return binding?.root
    }

    private fun createViewModel() =
        ViewModelProviders.of((activity as FragmentActivity), viewModelFactory)
            .get(this.vmClassToken)

    private fun setUI() {
        binding?.viewDelete?.setOnClickListener {
            deleteRecipe.invoke()
            dismiss()
        }
    }
}