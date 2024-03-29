package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.photo

import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipePhotoViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

}