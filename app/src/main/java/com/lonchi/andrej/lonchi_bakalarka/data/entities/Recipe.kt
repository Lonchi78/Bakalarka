package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.TypeConverters
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
data class Recipe (
    @Json(name = "id") var id: Long = -1,
    @Json(name = "vegetarian") var vegetarian: Boolean? = null,
    @Json(name = "vegan") var vegan: Boolean? = null,
    @Json(name = "glutenFree") var glutenFree: Boolean? = null,
    @Json(name = "dairyFree") var dairyFree: Boolean? = null,
    @Json(name = "veryHealthy") var veryHealthy: Boolean? = null,
    @Json(name = "cheap") var cheap: Boolean? = null,
    @Json(name = "veryPopular") var veryPopular: Boolean? = null,
    @Json(name = "sustainable") var sustainable: Boolean? = null,
    @Json(name = "lowFodmap") var lowFodmap: Boolean? = null,
    @Json(name = "aggregateLikes") var aggregateLikes: Int? = 0,
    @Json(name = "weightWatcherSmartPoints") var weightWatcherSmartPoints: Float? = 0f,
    @Json(name = "spoonacularScore") var spoonacularScore: Float? = 0f,
    @Json(name = "healthScore") var healthScore: Float? = 0f,
    @Json(name = "pricePerServing") var pricePerServing: Float? = 0f,
    @Json(name = "gaps") var gaps: String? = null,
    @Json(name = "creditsText") var creditsText: String? = null,
    @Json(name = "license") var license: String? = null,
    @Json(name = "sourceName") var sourceName: String? = null,
    @Json(name = "title") var title: String? = null,
    @Json(name = "readyInMinutes") var readyInMinutes: Int? = null,
    @Json(name = "servings") var servings: Int? = null,
    @Json(name = "sourceUrl") var sourceUrl: String? = null,
    @Json(name = "image") var image: String? = null,
    @Json(name = "imageType") var imageType: String? = null,
    @Json(name = "summary") var summary: String? = null,
    @Json(name = "originalId") var originalId: String? = null,
    @Json(name = "spoonacularSourceUrl") var spoonacularSourceUrl: String? = null,
    @Json(name = "cuisines") var cuisines: List<String?>? = listOf(),
    @Json(name = "dishTypes") var dishTypes: List<String?>? = listOf(),
    @Json(name = "diets") var diets: List<String?>? = listOf()
    //@Json(name = "diets") @TypeConverters(BteSpaceTypeConverters::class) var diets: List<BteSpace>? = listOf()
)