package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CustomRecipe : Recipe() {

    @PrimaryKey
    var id: String = this.idRestApi.toString()

    override fun toString(): String {
        return "Custom recipe: ${super.toString()}"
    }
}