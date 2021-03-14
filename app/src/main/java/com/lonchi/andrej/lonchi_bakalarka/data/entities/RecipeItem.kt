package com.lonchi.andrej.lonchi_bakalarka.data.entities

import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface RecipeItem {

    fun getId(): String

    fun getIdType(): RecipeIdTypeEnum

    fun getName(): String

    fun getImageUrl(): String

    fun getCookingTime(): Int

    fun getNumberOfIngredients(): Int

    fun getNumberOfInstructions(): Int

    fun getAllNutritions(): NutritionWrapper?

    fun getAllIngredients(): List<Ingredient>?

    fun getAllInstructions(): List<Instruction>?

    fun getAllDiets(): List<String?>?

    fun getAllCuisines(): List<String?>?

    fun getAllIntolerances(): List<String?>?

    fun getRecipeSourceUrl(): String?

}