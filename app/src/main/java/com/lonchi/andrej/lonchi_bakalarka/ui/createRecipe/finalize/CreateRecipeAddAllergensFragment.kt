package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddAllergensBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.allergens.adapter.AllergenRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddAllergensFragment : BaseFragment<CreateRecipeAddAllergensViewModel, FragmentCreateRecipeAddAllergensBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddAllergensFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_allergens
    override val vmClassToken: Class<CreateRecipeAddAllergensViewModel> = CreateRecipeAddAllergensViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddAllergensBinding = { FragmentCreateRecipeAddAllergensBinding.bind(it) }

    private val adapterAllergens by lazy {
        AllergenRowsAdapter(
            context = requireContext(),
            removeAllergen = { viewModel.removeAllergen(it.name) },
            addAllergen = { viewModel.addAllergen(it.name) }
        )
    }

    override fun initView() {
        //  TODO - add counter chip
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonSave?.setOnClickListener { saveIntolerances() }

        binding?.recyclerIntolerances?.adapter = adapterAllergens
        binding?.recyclerIntolerances?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        viewModel.intolerances.observe(viewLifecycleOwner) {
            adapterAllergens.submitList(it)
        }
    }

    private fun saveIntolerances() {
        viewModel.saveAllergens()
        requireActivity().onBackPressed()
    }
}