package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.ActivityCreateRecipeBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeActivity : BaseActivity<CreateRecipeViewModel, ActivityCreateRecipeBinding>() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, CreateRecipeActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }

        private enum class ProgressSteps {
            NAME,
            TIME,
            INGREDIENTS,
            INSTRUCTIONS,
            PHOTO,
            FINALIZE
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityCreateRecipeBinding =
        { ActivityCreateRecipeBinding.inflate(it) }
    override val vmClassToken: Class<CreateRecipeViewModel> = CreateRecipeViewModel::class.java

    override fun initView() {
        val navController = Navigation.findNavController(this, R.id.createRecipeNavHostFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleCurrentProgress(destination)
        }
    }

    override fun bindViewModel() = Unit

    private fun handleCurrentProgress(destination: NavDestination) {
        handleProgressVisibility(destination.id != R.id.successFragment)

        when (destination.id) {
            R.id.nameFragment -> setProgressIndicators(ProgressSteps.NAME.ordinal)
            R.id.timeFragment -> setProgressIndicators(ProgressSteps.TIME.ordinal)
            R.id.ingredientsFragment -> setProgressIndicators(ProgressSteps.INGREDIENTS.ordinal)
            R.id.instructionsFragment -> setProgressIndicators(ProgressSteps.INSTRUCTIONS.ordinal)
            R.id.photoFragment -> setProgressIndicators(ProgressSteps.PHOTO.ordinal)
            R.id.finalizeFragment -> setProgressIndicators(ProgressSteps.FINALIZE.ordinal)
        }
    }

    private fun handleProgressVisibility(visibility: Boolean) {
        binding.groupProgressIndicator.setVisible(visibility)
    }

    private fun setProgressIndicators(currentProgress: Int) {
        binding.progressIndicator1.background.setTint(
            ContextCompat.getColor(
                this,
                if (ProgressSteps.NAME.ordinal <= currentProgress) R.color.green500 else R.color.gray200
            )
        )
        binding.progressIndicator2.background.setTint(
            ContextCompat.getColor(
                this,
                if (ProgressSteps.TIME.ordinal <= currentProgress) R.color.green500 else R.color.gray200
            )
        )
        binding.progressIndicator3.background.setTint(
            ContextCompat.getColor(
                this,
                if (ProgressSteps.INGREDIENTS.ordinal <= currentProgress) R.color.green500 else R.color.gray200
            )
        )
        binding.progressIndicator4.background.setTint(
            ContextCompat.getColor(
                this,
                if (ProgressSteps.INSTRUCTIONS.ordinal <= currentProgress) R.color.green500 else R.color.gray200
            )
        )
        binding.progressIndicator5.background.setTint(
            ContextCompat.getColor(
                this,
                if (ProgressSteps.PHOTO.ordinal <= currentProgress) R.color.green500 else R.color.gray200
            )
        )
        binding.progressIndicator6.background.setTint(
            ContextCompat.getColor(
                this,
                if (ProgressSteps.FINALIZE.ordinal <= currentProgress) R.color.green500 else R.color.gray200
            )
        )
    }
}