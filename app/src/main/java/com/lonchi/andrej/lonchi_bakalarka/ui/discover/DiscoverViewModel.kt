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
                .subscribe({
                    Timber.d("getRandomRecipes status: ${it.status}")
                    Timber.d("getRandomRecipes errId: ${it.errorIdentification}")
                    Timber.d("getRandomRecipes recipes size: ${it.data?.recipes?.size}")
                    it.data?.recipes?.forEachIndexed { index, recipe ->
                        Timber.d("getRandomRecipes recipe $index: $recipe")
                    }
                }, {
                    Timber.e("getRandomRecipes: $it")
                })
        )
    }

}