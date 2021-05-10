package com.lonchi.andrej.lonchi_bakalarka.data.repository.rest

import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface RestApi {

    @GET("recipes/random")
    fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") numberOfResults: Int
    ): Single<Response<RecipesResponse>>

    @GET("food/jokes/random")
    fun getRandomJoke(
        @Query("apiKey") apiKey: String
    ): Single<Response<JokeResponse>>

    @GET("recipes/{id}/information")
    fun getRecipeDetail(
        @Path(value = "id", encoded = true) recipeId: String,
        @Query("apiKey") apiKey: String,
        @Query("includeNutrition") includeNutrition: Boolean? = true
    ): Single<Response<Recipe>>

    @GET("recipes/complexSearch")
    fun searchRecipesByQuery(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("diet") diet: String? = null,
        @Query("intolerances") intolerances: String? = null,
        @Query("minCalories") minCalories: Int? = null,
        @Query("maxCalories") maxCalories: Int? = null,
        @Query("minFat") minFat: Int? = null,
        @Query("maxFat") maxFat: Int? = null,
        @Query("minCarbs") minCarbs: Int? = null,
        @Query("maxCarbs") maxCarbs: Int? = null,
        @Query("minProtein") minProtein: Int? = null,
        @Query("maxProtein") maxProtein: Int? = null
    ): Single<Response<SearchRecipesResponse>>

    @GET("recipes/findByIngredients")
    fun searchRecipesByIngredients(
        @Query("apiKey") apiKey: String,
        @Query("ingredients") query: String
    ): Single<Response<List<Recipe>>>

}