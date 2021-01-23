package com.lonchi.andrej.lonchi_bakalarka.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.BuildConfig
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.User
import com.lonchi.andrej.lonchi_bakalarka.data.mappers.ObjectMappers.Companion.mapToFavouriteRecipe
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RecipesResponse
import com.lonchi.andrej.lonchi_bakalarka.data.utils.DeviceTracker
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface RecipesRepository {
    val loggedUser: LiveData<Resource<User>>

    fun addRecipeToFavourites(recipe: Recipe)
    fun deleteRecipeToFavourites(uid: String)

    fun getRandomRecipes(number: Int): Single<Resource<RecipesResponse>>
    fun getRecipeDetail(id: Long): Single<Resource<Recipe>>
}

class RecipesRepositoryImpl @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    private val prefs: SharedPreferencesInterface,
    retrofit: Retrofit,
    private val deviceTracker: DeviceTracker
) : BaseRepository(db, api, prefs, retrofit), RecipesRepository {

    override fun addRecipeToFavourites(recipe: Recipe) {
        db.favouriteRecipesDao().saveRecipe(recipe.mapToFavouriteRecipe(moshi))
        //  TODO - add to firebase db
    }

    override fun deleteRecipeToFavourites(uid: String) {
        db.favouriteRecipesDao().deleteRecipe(uid)
        //  TODO - delete from firebase db
    }

    /**
     * Get logged user state based on data in database
     * If user is not in db than user is not logged and Resource contains error
     * If user is in db Resource is success with user data
     */
    override val loggedUser: LiveData<Resource<User>> = Transformations.map(db.userDao().listAll()) {
        it.firstOrNull()?.let { Resource.success(it) }
                ?: Resource.error(ErrorIdentification.Authentication(), null)
    }

    override fun getRandomRecipes(number: Int): Single<Resource<RecipesResponse>> =
        api.getRandomRecipes(
            apiKey = BuildConfig.API_KEY,
            numberOfResults = number
        )
            .asSyncOperation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getRecipeDetail(id: Long): Single<Resource<Recipe>> =
        api.getRecipeDetail(
            apiKey = BuildConfig.API_KEY,
            recipeId = id.toString()
        )
            .asSyncOperation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}