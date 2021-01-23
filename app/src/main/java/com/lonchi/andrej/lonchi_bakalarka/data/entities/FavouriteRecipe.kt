package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavouriteRecipe : Recipe() {

    @PrimaryKey
    var uid: String = ""

    var isFavourite: Boolean = true

    override fun toString(): String {
        //  TODO
        return "Favourite recipe: { " +
                "recipeId = $uid\nisFavourite = $isFavourite\ntitle = $title\nimage = $image\n" +
                "}"
    }
}