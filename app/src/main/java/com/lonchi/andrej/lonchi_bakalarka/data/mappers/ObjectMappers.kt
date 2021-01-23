package com.lonchi.andrej.lonchi_bakalarka.data.mappers

import com.lonchi.andrej.lonchi_bakalarka.data.entities.FavouriteRecipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.squareup.moshi.Moshi

class ObjectMappers {

    companion object {

        fun Recipe.mapToFavouriteRecipe(moshi: Moshi): FavouriteRecipe {
            val json = moshi.adapter(Recipe::class.java).toJson(this)
            return moshi.adapter(FavouriteRecipe::class.java).fromJson(json).apply {
                this.uid = this@mapToFavouriteRecipe.idRestApi.toString()
            }
        }

    }
}