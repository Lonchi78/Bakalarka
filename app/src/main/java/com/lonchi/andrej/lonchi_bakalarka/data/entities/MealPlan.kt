package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum
import com.squareup.moshi.Json

@Entity
class MealPlan() {
    @PrimaryKey
    var date: String = ""
    var breakfast: List<Recipe>? = listOf()
    var lunch: List<Recipe>? = listOf()
    var dinner: List<Recipe>? = listOf()

    override fun toString(): String {
        return "MealPan { date = $date, breakfast = $breakfast, lunch = $lunch, dinner = $dinner }"
    }
}