package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.IngredientTypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.InstructionsWrapperTypeConverters
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
data class Recipe (
    @Json(name = "id") var id: Long = -1,
    @Json(name = "spoonacularScore") var spoonacularScore: Float? = 0f,
    @Json(name = "title") var title: String? = null,
    @Json(name = "readyInMinutes") var readyInMinutes: Int? = null,
    @Json(name = "servings") var servings: Int? = null,
    @Json(name = "healthScore") var healthScore: Float? = 0f,
    @Json(name = "image") var image: String? = null,
    @Json(name = "imageType") var imageType: String? = null,

    @Json(name = "sourceUrl") var sourceUrl: String? = null,
    @Json(name = "creditsText") var creditsText: String? = null,
    @Json(name = "license") var license: String? = null,
    @Json(name = "sourceName") var sourceName: String? = null,
    @Json(name = "spoonacularSourceUrl") var spoonacularSourceUrl: String? = null,

    @Json(name = "cuisines") var cuisines: List<String?>? = listOf(),
    @Json(name = "diets") var diets: List<String?>? = listOf(),

    @Json(name = "nutrition") @Embedded(prefix = "nutrition_") var nutrition: NutritionWrapper? = null,
    @Json(name = "extendedIngredients") @TypeConverters(IngredientTypeConverters::class) var ingredients: List<Ingredient>? = listOf(),
    @Json(name = "analyzedInstructions") @TypeConverters(InstructionsWrapperTypeConverters::class) var instructions: List<InstructionsWrapper>? = listOf()
)