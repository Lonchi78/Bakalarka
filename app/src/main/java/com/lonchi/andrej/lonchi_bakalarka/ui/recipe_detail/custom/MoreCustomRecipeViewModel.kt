package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.custom

import com.lonchi.andrej.lonchi_bakalarka.data.repository.CreateRecipeRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MoreCustomRecipeViewModel @Inject constructor(
    private val createRecipeRepository: CreateRecipeRepository
) : BaseViewModel() {
    fun deleteRecipe(recipeId: String) = createRecipeRepository.deleteRecipe(recipeId)
}