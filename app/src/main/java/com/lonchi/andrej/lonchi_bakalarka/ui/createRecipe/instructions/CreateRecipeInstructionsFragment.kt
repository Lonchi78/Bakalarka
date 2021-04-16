package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.instructions

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeCustom
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeInstructionsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.InstructionRowsAdapter

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeInstructionsFragment : BaseFragment<CreateRecipeInstructionsViewModel, FragmentCreateRecipeInstructionsBinding>() {
    companion object {
        fun newInstance() = CreateRecipeInstructionsFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_instructions
    override val vmClassToken: Class<CreateRecipeInstructionsViewModel> = CreateRecipeInstructionsViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeInstructionsBinding = { FragmentCreateRecipeInstructionsBinding.bind(it) }

    private val adapterInstructions by lazy {
        InstructionRowsAdapter(
            context = requireContext()
        )
    }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            //findNavController().navigate(CreateRecipeInstructionsFragmentDirections.actionInstructionsFragmentToPhotoFragment())
            findNavController().navigate(CreateRecipeInstructionsFragmentDirections.actionInstructionsFragmentToFinalizeFragment())
        }
        binding?.buttonAddInstruction?.setOnClickListener {
            findNavController().navigate(CreateRecipeInstructionsFragmentDirections.actionInstructionsFragmentToAddInstructionFragment())
        }

        binding?.recyclerInstructions?.adapter = adapterInstructions
        binding?.recyclerInstructions?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        viewModel.newRecipe.observe(viewLifecycleOwner) {
            handleRecipeInstructions(it)
        }
    }

    private fun handleRecipeInstructions(recipe: Resource<RecipeCustom>) {
        //  TODO - allow to edit instructions
        binding?.buttonNext?.isEnabled = (recipe.data?.getNumberOfInstructions() ?: 0) != 0

        if (recipe.status is SuccessStatus) {
            adapterInstructions.submitList(recipe.data?.getAllInstructions()?.sortedBy { it.number })
        }
    }
}