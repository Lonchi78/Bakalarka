package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.IngredientTypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.InstructionsWrapperTypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.ListOfStringsTypeConverters
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
open class Recipe : RecipeItem {
    @Json(name = "id") var idRestApi: Long = -1
    @Json(name = "title") var title: String? = null
    @Json(name = "readyInMinutes") var readyInMinutes: Int? = null
    @Json(name = "image") var image: String? = null
    @Json(name = "imageType") var imageType: String? = null
    @Json(name = "servings") var servings: Int? = null
    @Json(name = "healthScore") var healthScore: Float? = 0f

    @Json(name = "sourceUrl") var sourceUrl: String? = null
    @Json(name = "creditsText") var creditsText: String? = null
    @Json(name = "license") var license: String? = null
    @Json(name = "sourceName") var sourceName: String? = null
    @Json(name = "spoonacularSourceUrl") var spoonacularSourceUrl: String? = null

    @TypeConverters(ListOfStringsTypeConverters::class)
    @Json(name = "cuisines") var cuisines: List<String?>? = listOf()

    @TypeConverters(ListOfStringsTypeConverters::class)
    @Json(name = "diets") var diets: List<String?>? = listOf()

    @Json(name = "nutrition") @Embedded(prefix = "nutrition_") var nutrition: NutritionWrapper? = null

    @TypeConverters(IngredientTypeConverters::class)
    @Json(name = "extendedIngredients") var ingredients: List<Ingredient>? = listOf()

    @TypeConverters(InstructionsWrapperTypeConverters::class)
    @Json(name = "analyzedInstructions") var instructions: List<InstructionsWrapper>? = listOf()

    override fun getRecipeId(): String = this.idRestApi.toString()
}