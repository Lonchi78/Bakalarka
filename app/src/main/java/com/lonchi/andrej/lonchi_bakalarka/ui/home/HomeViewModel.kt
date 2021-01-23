package com.lonchi.andrej.lonchi_bakalarka.ui.home

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class HomeViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

    companion object {
        private const val DEFAULT_RANDOM_RECIPES = 5
    }

    val stateRandomRecipes: MutableLiveData<Resource<List<Recipe>>> = MutableLiveData<Resource<List<Recipe>>>().apply {
        postValue(Resource.notStarted())
    }

    init {
        getRandomRecipes()
    }

    private fun getRandomRecipes() {
        compositeDisposable.add(
            recipesRepository.getRandomRecipes(DEFAULT_RANDOM_RECIPES)
                .doOnSubscribe { stateRandomRecipes.postValue(Resource.loading()) }
                .subscribe({
                    Timber.d("getRandomRecipes status: ${it.status}")
                    Timber.d("getRandomRecipes status: ${it.status is SuccessStatus}")
                    Timber.d("getRandomRecipes data: ${it.data?.recipes}")
                    Timber.d("getRandomRecipes data: ${it.data?.recipes.isNullOrEmpty()}")
                    Timber.d("getRandomRecipes data: ${!it.data?.recipes.isNullOrEmpty()}")
                    if (it.status is SuccessStatus && !it.data?.recipes.isNullOrEmpty()) {
                        Timber.d("getRandomRecipes: ok")
                        stateRandomRecipes.postValue(Resource.success(it.data?.recipes))
                    } else {
                        Timber.d("getRandomRecipes: err")
                        stateRandomRecipes.postValue(Resource.error(it.errorIdentification))
                    }
                }, {
                    Timber.e("GetRandomRecipes fatal error! $it")
                    stateRandomRecipes.postValue(Resource.error(ErrorIdentification.Unknown(it.message)))
                })
        )
    }
}