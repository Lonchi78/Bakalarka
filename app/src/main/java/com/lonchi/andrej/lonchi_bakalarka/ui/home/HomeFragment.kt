package com.lonchi.andrej.lonchi_bakalarka.ui.home

import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentHomeBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.CameraActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.recipes.RecipeCardsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.recipes.RecipeCardsColumnAdapter


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int = R.layout.fragment_home
    override val vmClassToken: Class<HomeViewModel> = HomeViewModel::class.java
    override val bindingInflater: (View) -> FragmentHomeBinding = { FragmentHomeBinding.bind(it) }

    private val adapterRandomRecipes by lazy {
        RecipeCardsAdapter(
            context = requireContext(),
            onItemClick = { onRecipeItemClick(it) }
        )
    }

    private val adapterFavouriteRecipes by lazy {
        RecipeCardsColumnAdapter(
            context = requireContext(),
            onItemClick = { onRecipeItemClick(it) }
        )
    }

    override fun initView() {
        binding?.recyclerRandomRecipes?.adapter = adapterRandomRecipes
        binding?.recyclerRandomRecipes?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        LinearSnapHelper().attachToRecyclerView(binding?.recyclerRandomRecipes)

        binding?.recyclerFavouriteRecipes?.adapter = adapterFavouriteRecipes
        binding?.recyclerFavouriteRecipes?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        ).apply {
            isMeasurementCacheEnabled = false
        }

        binding?.buttonCreateRecipe?.setOnClickListener {
            startActivity(CameraActivity.getStartIntent(requireContext()))
        }
    }

    override fun bindViewModel() {
        viewModel.stateRandomRecipes.observe(viewLifecycleOwner) {
            showProgressDialog(it.status is LoadingStatus)

            when (it.status) {
                is SuccessStatus -> {
                    adapterRandomRecipes.submitList(it.data)
                    adapterFavouriteRecipes.submitList(it.data)
                    binding?.chipCounterFavourites?.text = (0..10).random().toString()
                }
                is ErrorStatus -> {
                    //  TODO - add error state or hide section, show connectivity problem
                    showErrorSnackbar(it.errorIdentification)
                }
                is LoadingStatus -> {
                    //  TODO - show loading status?
                }
                else -> Unit
            }
        }
    }

    private fun onRecipeItemClick(recipe: RecipeItem) {
        Toast.makeText(
            requireContext(),
            "onRecipeItemClick ${recipe.getName()}",
            Toast.LENGTH_SHORT
        ).show()
        findNavController().navigate(
            HomeFragmentDirections.actionGlobalRecipeDetailFragment(
                recipeId = recipe.getId(),
                idType = recipe.getIdType()
            )
        )
    }

}