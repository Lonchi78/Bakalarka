package com.lonchi.andrej.lonchi_bakalarka.ui.home

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.JokeResponse
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
    private val recipesRepository: RecipesRepository,
    private val recipeRepository: RecipesRepository
) : BaseViewModel() {

    companion object {
        private const val DEFAULT_RANDOM_RECIPES = 5
    }

    val stateRandomRecipes: MutableLiveData<Resource<List<Recipe>>> = MutableLiveData<Resource<List<Recipe>>>().apply {
        postValue(Resource.notStarted())
    }
    val stateRandomJoke: MutableLiveData<Resource<JokeResponse>> = MutableLiveData<Resource<JokeResponse>>().apply {
        postValue(Resource.notStarted())
    }
    val randomIngredients: MutableLiveData<List<String>> = MutableLiveData<List<String>>().apply {
        //  TODO - add random generator from more options
        val ingredients = listOf(
            "Apple",
            "Chicken",
            "Tomato",
            "Cheese",
            "Garlic",
            "Salad",
            "Mustard",
            "Tuna",
            "Pumpkin seeds",
            "Soy sauce"
        )
        postValue(ingredients.sortedBy { it.getOrNull((0..2).random()) })
    }

    init {
        getRandomRecipes()
        getRandomJoke()
    }

    fun getAllFavouritesRecipes() = recipeRepository.getAllFavouritesRecipes()
    fun getAllCustomRecipes() = recipeRepository.getAllCustomRecipes()

    private fun getRandomRecipes() {
        compositeDisposable.add(
            recipesRepository.getRandomRecipes(DEFAULT_RANDOM_RECIPES)
                .doOnSubscribe { stateRandomRecipes.postValue(Resource.loading()) }
                .subscribe({
                    if (it.status is SuccessStatus && !it.data?.recipes.isNullOrEmpty()) {
                        stateRandomRecipes.postValue(Resource.success(it.data?.recipes))
                    } else {
                        stateRandomRecipes.postValue(Resource.error(it.errorIdentification))
                    }
                }, {
                    Timber.e("GetRandomRecipes fatal error! $it")
                    stateRandomRecipes.postValue(Resource.error(ErrorIdentification.Unknown(it.message)))
                })
        )
    }

    private fun getRandomJoke() {
        compositeDisposable.add(
            recipesRepository.getRandomJoke()
                .doOnSubscribe { stateRandomJoke.postValue(Resource.loading()) }
                .subscribe({
                    if (it.status is SuccessStatus && it.data?.joke != null) {
                        stateRandomJoke.postValue(Resource.success(it.data))
                    } else {
                        stateRandomJoke.postValue(Resource.error(it.errorIdentification))
                    }
                }, {
                    Timber.e("GetRandomRecipes fatal error! $it")
                    stateRandomJoke.postValue(Resource.error(ErrorIdentification.Unknown(it.message)))
                })
        )
    }
}