package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

    fun getRandomRecipes() {
        Timber.d("getRandomRecipes:")
        compositeDisposable.add(
            recipesRepository.getRandomRecipes(5)
                .subscribe({ response ->
                    Timber.d("getRandomRecipes status: ${response.status}")
                    Timber.d("getRandomRecipes errId: ${response.errorIdentification}")
                    Timber.d("getRandomRecipes recipes size: ${response.data?.recipes?.size}")
                    response.data?.recipes?.firstOrNull()?.let {
                        Timber.d("Random recipe: $it")
                        Timber.d("Random recipe: ${it.title}")
                        Timber.d("Random recipe: ${it.image}")
                        Timber.d("- - -")
                        Timber.d("Random recipe nutrition: ${it.nutrition?.nutrients?.size}")
                        Timber.d("Random recipe nutrition: ${it.nutrition?.nutrients?.firstOrNull()?.name}")
                        Timber.d("Random recipe nutrition: ${it.nutrition?.nutrients?.firstOrNull()?.amount}")
                        Timber.d("- - -")
                        Timber.d("Random recipe instructions: ${it.instructions?.size}")
                        Timber.d("Random recipe instructions: ${it.instructions?.firstOrNull()?.name}")
                        Timber.d("Random recipe instructions: ${it.instructions?.firstOrNull()?.steps?.firstOrNull()?.step}")
                        Timber.d("Random recipe instructions: ${it.instructions?.firstOrNull()?.steps?.firstOrNull()?.number}")
                        Timber.d("- - -")
                        Timber.d("Random recipe ingredients: ${it.ingredients?.size}")
                        Timber.d("Random recipe ingredients: ${it.ingredients?.firstOrNull()?.id}")
                        Timber.d("Random recipe ingredients: ${it.ingredients?.firstOrNull()?.image}")
                        Timber.d("Random recipe ingredients: ${it.ingredients?.firstOrNull()?.name}")
                        Timber.d("- - -")
                    }
                }, {
                    Timber.e("getRandomRecipes: $it")
                })
        )
    }

}