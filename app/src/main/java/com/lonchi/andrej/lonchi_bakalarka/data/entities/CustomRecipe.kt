package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CustomRecipe : RecipeItem, Recipe() {

    @PrimaryKey
    var uid: String = this.idRestApi.toString()

    override fun getRecipeId(): String = this.uid

    override fun toString(): String {
        return "Custom recipe: ${super.toString()}"
    }
}