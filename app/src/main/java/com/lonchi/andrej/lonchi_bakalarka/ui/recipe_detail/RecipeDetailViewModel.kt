package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class RecipeDetailViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

    val stateRecipeDetail: MutableLiveData<Resource<RecipeItem>> = MutableLiveData<Resource<RecipeItem>>().apply {
        postValue(Resource.notStarted())
    }

    var recipeId: String? = null
    var recipeIdType: RecipeIdTypeEnum? = null

    fun handleInputArguments(recipeId: String, recipeIdType: RecipeIdTypeEnum) {
        when (recipeIdType) {
            RecipeIdTypeEnum.FAVOURITE_RECIPE -> getFavouriteRecipeDetail(recipeId)
            RecipeIdTypeEnum.OWN_RECIPE -> getOwnRecipeDetail(recipeId)
            RecipeIdTypeEnum.REST -> getRestRecipeDetail(recipeId)
        }
        this.recipeId = recipeId
        this.recipeIdType = recipeIdType
    }

    private fun getFavouriteRecipeDetail(uid: String) {
        compositeDisposable.add(
            recipesRepository.getFavouriteRecipe(uid)
                .doOnSubscribe { stateRecipeDetail.postValue(Resource.loading()) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNullOrEmpty()) {
                        stateRecipeDetail.postValue(Resource.error(ErrorIdentification.Unknown()))
                    } else {
                        stateRecipeDetail.postValue(Resource.success(it.first()))
                    }
                }, {
                    Timber.e("getFavouriteRecipeDetail fatal error! $it")
                    stateRecipeDetail.postValue(Resource.error(ErrorIdentification.Unknown(it.message)))
                })
        )
    }

    private fun getOwnRecipeDetail(uid: String) {
        compositeDisposable.add(
            recipesRepository.getOwnRecipe(uid)
                .doOnSubscribe { stateRecipeDetail.postValue(Resource.loading()) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNullOrEmpty()) {
                        stateRecipeDetail.postValue(Resource.error(ErrorIdentification.Unknown()))
                    } else {
                        stateRecipeDetail.postValue(Resource.success(it.first()))
                    }
                }, {
                    Timber.e("getOwnRecipeDetail fatal error! $it")
                    stateRecipeDetail.postValue(Resource.error(ErrorIdentification.Unknown(it.message)))
                })
        )
    }

    private fun getRestRecipeDetail(uid: String) {
        compositeDisposable.add(
            recipesRepository.getRecipeDetail(uid.toLongOrNull() ?: -1)
                .doOnSubscribe { stateRecipeDetail.postValue(Resource.loading()) }
                .subscribe({
                    if (it.status is SuccessStatus && it.data != null) {
                        stateRecipeDetail.postValue(Resource.success(it.data))
                    } else {
                        stateRecipeDetail.postValue(Resource.error(it.errorIdentification))
                    }
                }, {
                    Timber.e("GetRestRecipeDetail fatal error! $it")
                    stateRecipeDetail.postValue(Resource.error(ErrorIdentification.Unknown(it.message)))
                })
        )
    }

}