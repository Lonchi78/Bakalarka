package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail

import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class RecipeDetailViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

    var recipeId: String? = null
    var recipeIdType: RecipeIdTypeEnum? = null

    fun getRecipeDetail() {
        Timber.d("getRecipeDetail:")
        compositeDisposable.add(
            recipesRepository.getRecipeDetail(634986)
                .subscribe({
                    Timber.d("getRecipeDetail status: ${it.status}")
                    Timber.d("getRecipeDetail errId: ${it.errorIdentification}")
                    Timber.d("- - -")
                    Timber.d("Random recipe: $it")
                    Timber.d("Random recipe: ${it.data?.title}")
                    Timber.d("Random recipe: ${it.data?.image}")
                    Timber.d("- - -")
                    Timber.d("Random recipe nutrition: ${it.data?.nutrition?.nutrients?.size}")
                    Timber.d("Random recipe nutrition: ${it.data?.nutrition?.nutrients?.firstOrNull()?.name}")
                    Timber.d("Random recipe nutrition: ${it.data?.nutrition?.nutrients?.firstOrNull()?.amount}")
                    Timber.d("- - -")
                    Timber.d("Random recipe instructions: ${it.data?.instructions?.size}")
                    Timber.d("Random recipe instructions: ${it.data?.instructions?.firstOrNull()?.name}")
                    Timber.d("Random recipe instructions: ${it.data?.instructions?.firstOrNull()?.steps?.firstOrNull()?.step}")
                    Timber.d("Random recipe instructions: ${it.data?.instructions?.firstOrNull()?.steps?.firstOrNull()?.number}")
                    Timber.d("- - -")
                    Timber.d("Random recipe ingredients: ${it.data?.ingredients?.size}")
                    Timber.d("Random recipe ingredients: ${it.data?.ingredients?.firstOrNull()?.id}")
                    Timber.d("Random recipe ingredients: ${it.data?.ingredients?.firstOrNull()?.image}")
                    Timber.d("Random recipe ingredients: ${it.data?.ingredients?.firstOrNull()?.name}")
                    Timber.d("- - -")
                }, {
                    Timber.e("getRandomRecipes: $it")
                })
        )
    }

}