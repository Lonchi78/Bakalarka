package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.lonchi.andrej.lonchi_bakalarka.databinding.ActivityCreateRecipeBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeActivity : BaseActivity<CreateRecipeViewModel, ActivityCreateRecipeBinding>() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, CreateRecipeActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityCreateRecipeBinding = { ActivityCreateRecipeBinding.inflate(it) }
    override val vmClassToken: Class<CreateRecipeViewModel> = CreateRecipeViewModel::class.java


    override fun initView() {
    }

    override fun bindViewModel() = Unit
}