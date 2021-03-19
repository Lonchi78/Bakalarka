package com.lonchi.andrej.lonchi_bakalarka.ui.favourites

import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class FavouritesViewModel @Inject constructor(
    private val recipeRepository: RecipesRepository
) : BaseViewModel() {

    fun getAllFavouritesRecipes() = recipeRepository.getAllFavouritesRecipes()

}