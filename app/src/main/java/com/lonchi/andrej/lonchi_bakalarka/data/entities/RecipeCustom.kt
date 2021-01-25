package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum

@Entity
class RecipeCustom : RecipeItem, Recipe() {

    @PrimaryKey
    var uid: String = this.idRestApi.toString()

    override fun toString(): String {
        return "Custom recipe: ${super.toString()}"
    }

    override fun getId(): String = this.uid

    override fun getIdType(): RecipeIdTypeEnum = RecipeIdTypeEnum.OWN_RECIPE

    override fun getName(): String = this.title ?: ""

    override fun getImageUrl(): String = this.image ?: ""

    override fun getCookingTime(): Int = this.readyInMinutes ?: 0

    override fun getNumberOfIngredients(): Int =this.ingredients?.size ?: 0

    override fun getNumberOfInstructions(): Int = this.instructions?.firstOrNull()?.steps?.size ?: 0

    override fun getAllNutritions(): NutritionWrapper? = this.nutrition

    override fun getAllIngredients(): List<Ingredient>? = this.ingredients

    override fun getAllInstructions(): List<Instruction>? = this.instructions?.firstOrNull()?.steps

    override fun getAllDiets(): List<String?>? = this.diets

    override fun getAllCuisines(): List<String?>? = this.cuisines

    override fun getRecipeSourceUrl(): String? = this.sourceUrl
}