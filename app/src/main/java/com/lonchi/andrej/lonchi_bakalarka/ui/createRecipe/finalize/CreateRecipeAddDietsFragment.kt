package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddDietsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.diets.adapter.DietRowsAdapter

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddDietsFragment :
    BaseFragment<CreateRecipeAddDietsViewModel, FragmentCreateRecipeAddDietsBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddDietsFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_diets
    override val vmClassToken: Class<CreateRecipeAddDietsViewModel> = CreateRecipeAddDietsViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddDietsBinding = { FragmentCreateRecipeAddDietsBinding.bind(it) }

    private val adapterDiets by lazy {
        DietRowsAdapter(
            context = requireContext(),
            removeDiet = { viewModel.removeDiet(it.name) },
            addDiet = { viewModel.addDiet(it.name) }
        )
    }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonSave?.setOnClickListener { saveDiets() }

        binding?.recyclerDiets?.adapter = adapterDiets
        binding?.recyclerDiets?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        viewModel.diets.observe(viewLifecycleOwner) { addDiets ->
            binding?.chipCounter?.text = addDiets.filter { it.isChecked }.size.toString()
            adapterDiets.submitList(addDiets)
        }
    }

    private fun saveDiets() {
        viewModel.saveDiets()
        requireActivity().onBackPressed()
    }
}