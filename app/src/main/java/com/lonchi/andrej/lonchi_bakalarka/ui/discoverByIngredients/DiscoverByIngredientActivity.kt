package com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.lonchi.andrej.lonchi_bakalarka.databinding.ActivityDiscoverByIngredientsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientActivity : BaseActivity<DiscoverByIngredientViewModel, ActivityDiscoverByIngredientsBinding>() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, DiscoverByIngredientActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityDiscoverByIngredientsBinding = { ActivityDiscoverByIngredientsBinding.inflate(it) }
    override val vmClassToken: Class<DiscoverByIngredientViewModel> = DiscoverByIngredientViewModel::class.java


    override fun initView() {
    }

    override fun bindViewModel() = Unit
}