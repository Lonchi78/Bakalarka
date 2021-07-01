package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients

import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.data.repository.CreateRecipeRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeIngredientsViewModel @Inject constructor(
    private val createRecipeRepository: CreateRecipeRepository
) : BaseViewModel() {

    val newRecipe = createRecipeRepository.newRecipe

    fun deleteIngredient(ingredient: Ingredient) {
        createRecipeRepository.deleteIngredient(ingredient)
    }
}