package com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.repository.DiscoverByIngredientsRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class IngredientsListViewModel @Inject constructor(
    private val rootRepository: DiscoverByIngredientsRepository,
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

    val ingredients: MutableLiveData<List<String>> = rootRepository.ingredients

    fun resetIngredients() = rootRepository.resetIngredients()

    fun removeIngredient(value: String) = rootRepository.removeIngredient(value)

    fun searchRecipesByIngredients() {
        Timber.d("searchRecipesByIngredients: ${ingredients.value.toString()}")
        compositeDisposable.add(
            recipesRepository.searchRecipesByIngredients(ingredients.value ?: listOf())
                .subscribe({ response ->
                    Timber.d("searchRecipesByIngredients status: ${response.status}")
                    Timber.d("searchRecipesByIngredients errId: ${response.errorIdentification}")
                    Timber.d("searchRecipesByIngredients recipes size: ${response.data?.size}")
                    //searchRecipeState.postValue(response)
                }, {
                    Timber.e("searchRecipesByIngredients: $it")
                    //searchRecipeState.postValue(Resource.error(ErrorIdentification.Unknown()))
                })
        )
    }

}