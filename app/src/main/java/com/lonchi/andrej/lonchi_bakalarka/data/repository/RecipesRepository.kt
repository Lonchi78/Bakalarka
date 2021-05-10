package com.lonchi.andrej.lonchi_bakalarka.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.BuildConfig
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.entities.*
import com.lonchi.andrej.lonchi_bakalarka.data.mappers.ObjectMappers.Companion.mapToFavouriteRecipe
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.JokeResponse
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RecipesResponse
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.SearchRecipesResponse
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.logic.util.toCommaSeparatedString
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

    fun getAllFavouritesRecipes(): LiveData<List<RecipeFavourite>>
    fun getAllCustomRecipes(): LiveData<List<RecipeCustom>>

    fun getFavouriteRecipe(uid: String): Single<List<RecipeFavourite>>
    fun getOwnRecipe(uid: String): Single<List<RecipeCustom>>

    fun addRecipeToFavourites(recipe: Recipe)
    fun removeRecipeToFavourites(uid: String)

    fun getRandomRecipes(number: Int): Single<Resource<RecipesResponse>>
    fun getRandomJoke(): Single<Resource<JokeResponse>>
    fun searchRecipesByQuery(query: String): Single<Resource<SearchRecipesResponse>>
    fun searchRecipesByIngredients(ingredients: List<String>): Single<Resource<List<Recipe>>>
    fun getRecipeDetail(id: Long): Single<Resource<Recipe>>
}

class RecipesRepositoryImpl @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    private val prefs: SharedPreferencesInterface,
    retrofit: Retrofit,
    private val userRepository: UserRepository
) : BaseRepository(db, api, prefs, retrofit), RecipesRepository {

    override fun getAllCustomRecipes(): LiveData<List<RecipeCustom>> {
        return db.customRecipesDao().getAllRecipes()
    }

    override fun getAllFavouritesRecipes(): LiveData<List<RecipeFavourite>> {
        return db.favouriteRecipesDao().getAllRecipes()
    }

    override fun getFavouriteRecipe(uid: String): Single<List<RecipeFavourite>> {
        return db.favouriteRecipesDao().getRecipe(uid)
    }

    override fun getOwnRecipe(uid: String): Single<List<RecipeCustom>> {
        return db.customRecipesDao().getRecipe(uid)
    }

    override fun addRecipeToFavourites(recipe: Recipe) {
        db.favouriteRecipesDao().insert(recipe.mapToFavouriteRecipe(moshi))
        userRepository.updateFavouriteRecipes()
    }

    override fun removeRecipeToFavourites(uid: String) {
        db.favouriteRecipesDao().deleteRecipe(uid)
        userRepository.updateFavouriteRecipes()
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

    override fun getRandomJoke(): Single<Resource<JokeResponse>> =
        api.getRandomJoke(
            apiKey = BuildConfig.API_KEY
        )
            .asSyncOperation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun searchRecipesByQuery(query: String): Single<Resource<SearchRecipesResponse>> {
        val filterResource = userRepository.actualFilter.value
        val filterData = filterResource?.data

        if (filterResource?.status is SuccessStatus && filterData != null && filterData != Filter()) {
            val diets = userRepository.getUserDietsBlocking()
            val intolerances = userRepository.getUserIntolerancesBlocking()

            return api.searchRecipesByQuery(
                apiKey = BuildConfig.API_KEY,
                query = query,
                diet = diets.firstOrNull()?.diets?.toCommaSeparatedString(),
                intolerances = intolerances.firstOrNull()?.intolerances?.toCommaSeparatedString(),
                minCalories = filterData.caloriesMin,
                maxCalories = filterData.caloriesMax,
                minFat = filterData.fatMin,
                maxFat = filterData.fatMax,
                minCarbs = filterData.carbsMin,
                maxCarbs = filterData.carbsMax,
                minProtein = filterData.proteinMin,
                maxProtein = filterData.proteinMax
            )
                .asSyncOperation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        } else {
            return api.searchRecipesByQuery(
                apiKey = BuildConfig.API_KEY,
                query = query
            )
                .asSyncOperation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    override fun searchRecipesByIngredients(ingredients: List<String>): Single<Resource<List<Recipe>>> =
        api.searchRecipesByIngredients(
            apiKey = BuildConfig.API_KEY,
            query = ingredients.toCommaSeparatedString()
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