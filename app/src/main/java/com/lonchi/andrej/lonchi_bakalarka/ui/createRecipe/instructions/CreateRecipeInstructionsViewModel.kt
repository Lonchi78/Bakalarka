package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.instructions

import com.lonchi.andrej.lonchi_bakalarka.data.repository.CreateRecipeRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeInstructionsViewModel @Inject constructor(
    private val createRecipeRepository: CreateRecipeRepository
) : BaseViewModel() {

    val newRecipe = createRecipeRepository.newRecipe
}