package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.repository.DiscoverByIngredientsRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsResultsViewModel @Inject constructor(
    private val rootRepository: DiscoverByIngredientsRepository,
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

    val searchRecipeState: MutableLiveData<Resource<List<Recipe>>> = MutableLiveData<Resource<List<Recipe>>>().apply {
        postValue(Resource.notStarted())
    }

    fun searchRecipes() {
        val ingredients = rootRepository.ingredients.value ?: listOf()
        compositeDisposable.add(
            recipesRepository.searchRecipesByIngredients(ingredients)
                .doOnSubscribe {
                    searchRecipeState.postValue(Resource.loading())
                }
                .subscribe({ response ->
                    Timber.d("searchRecipesByIngredients status: ${response.status}")
                    Timber.d("searchRecipesByIngredients errId: ${response.errorIdentification}")
                    Timber.d("searchRecipesByIngredients recipes size: ${response.data?.size}")
                    searchRecipeState.postValue(response)
                }, {
                    Timber.e("searchRecipesByIngredients: $it")
                    searchRecipeState.postValue(Resource.error(ErrorIdentification.Unknown()))
                })
        )
    }
}