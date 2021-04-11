package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.lonchi.andrej.lonchi_bakalarka.databinding.ActivityDiscoverByIngredientsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsActivity : BaseActivity<DiscoverByIngredientsViewModel, ActivityDiscoverByIngredientsBinding>() {

    companion object {
        fun getStartIntent(context: Context, previewIngredient: String? = null): Intent =
            Intent(context, DiscoverByIngredientsActivity::class.java)
                .apply {
                    if (!previewIngredient.isNullOrEmpty()) {
                        this.putExtras(
                            Bundle().apply {
                                putString(BUNDLE_KEY_PREVIEW_INGREDIENT, previewIngredient)
                            }
                        )
                    }
                }

        private const val BUNDLE_KEY_PREVIEW_INGREDIENT = "bundle.key.preview.ingredient"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityDiscoverByIngredientsBinding = { ActivityDiscoverByIngredientsBinding.inflate(it) }
    override val vmClassToken: Class<DiscoverByIngredientsViewModel> = DiscoverByIngredientsViewModel::class.java

    override fun initView() = Unit
    override fun bindViewModel() {
        handleInputArguments()
    }

    override fun onDestroy() {
        viewModel.resetIngredients()
        super.onDestroy()
    }

    private fun handleInputArguments() {
        intent.extras?.getString(BUNDLE_KEY_PREVIEW_INGREDIENT)?.let {
            viewModel.addIngredient(it)
        }
    }
}