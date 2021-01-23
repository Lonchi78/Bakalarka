package com.lonchi.andrej.lonchi_bakalarka.data.mappers

import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeFavourite
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.squareup.moshi.Moshi

class ObjectMappers {

    companion object {

        fun Recipe.mapToFavouriteRecipe(moshi: Moshi): RecipeFavourite {
            val json = moshi.adapter(Recipe::class.java).toJson(this)
            return moshi.adapter(RecipeFavourite::class.java).fromJson(json).apply {
                this.uid = this@mapToFavouriteRecipe.idRestApi.toString()
            }
        }

    }
}