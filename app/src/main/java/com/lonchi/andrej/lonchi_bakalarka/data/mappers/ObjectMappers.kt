package com.lonchi.andrej.lonchi_bakalarka.data.mappers

import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeFavourite
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.squareup.moshi.Moshi

class ObjectMappers {

    companion object {

        fun Recipe.mapToFavouriteRecipe(moshi: Moshi): RecipeFavourite {
            val json = moshi.adapter(Recipe::class.java).toJson(this)
            val favouriteRecipe = moshi.adapter(RecipeFavourite::class.java).fromJson(json).apply {
                this.uid = this@mapToFavouriteRecipe.idRestApi.toString()
            }

            //  Remove unused nutrition
            val tmp = listOf(
                favouriteRecipe?.nutrition?.getCarbohydrates(),
                favouriteRecipe?.nutrition?.getCalories(),
                favouriteRecipe?.nutrition?.getProtein(),
                favouriteRecipe?.nutrition?.getFat()
            )
            favouriteRecipe?.nutrition?.nutrients = tmp

            return favouriteRecipe
        }

    }
}