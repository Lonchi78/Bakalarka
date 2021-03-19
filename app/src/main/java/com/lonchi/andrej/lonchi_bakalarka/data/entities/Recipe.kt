package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.IngredientTypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.InstructionsWrapperTypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.ListOfStringsTypeConverters
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
open class Recipe : RecipeItem {
    @Json(name = "id") var idRestApi: Long = -1
    @Json(name = "title") var title: String? = null
    @Json(name = "readyInMinutes") var readyInMinutes: Int? = null
    @Json(name = "image") var image: String? = null
    @Json(name = "sourceUrl") var sourceUrl: String? = null
    @Json(name = "nutrition") @Embedded(prefix = "nutrition_") var nutrition: NutritionWrapper? = null

    @TypeConverters(ListOfStringsTypeConverters::class)
    @Json(name = "cuisines") var cuisines: List<String?>? = listOf()

    @TypeConverters(ListOfStringsTypeConverters::class)
    @Json(name = "diets") var diets: List<String?>? = listOf()

    @TypeConverters(ListOfStringsTypeConverters::class)
    @Json(name = "intolerances") var intolerances: List<String?>? = listOf()

    @TypeConverters(IngredientTypeConverters::class)
    @Json(name = "extendedIngredients") var ingredients: List<Ingredient>? = listOf()

    @TypeConverters(InstructionsWrapperTypeConverters::class)
    @Json(name = "analyzedInstructions") var instructions: List<InstructionsWrapper>? = listOf()

    override fun getId(): String = this.idRestApi.toString()

    override fun getRecipeIdType(): Int = RecipeIdTypeEnum.REST.ordinal

    override fun getName(): String = this.title ?: ""

    override fun getImageUrl(): String = this.image ?: ""

    override fun getCookingTime(): Int = this.readyInMinutes ?: 0

    override fun getNumberOfIngredients(): Int = this.ingredients?.size ?: 0

    override fun getNumberOfInstructions(): Int = this.instructions?.firstOrNull()?.steps?.size ?: 0

    override fun getAllNutritions(): NutritionWrapper? = this.nutrition

    override fun getAllIngredients(): List<Ingredient>? = this.ingredients

    override fun getAllInstructions(): List<Instruction>? = this.instructions?.firstOrNull()?.steps

    override fun getAllDiets(): List<String?>? = this.diets

    override fun getAllIntolerances(): List<String?>? = this.intolerances

    override fun getAllCuisines(): List<String?>? = this.cuisines

    override fun getRecipeSourceUrl(): String? = this.sourceUrl
}