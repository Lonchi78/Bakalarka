package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Diets (
    @PrimaryKey var id: Long = -1,
    var glutenFree: Boolean? = false,
    var ketogenic: Boolean? = false,
    var vegetarian: Boolean? = false,
    var lactoVegetarian: Boolean? = false,
    var ovoVegetarian: Boolean? = false,
    var vegan: Boolean? = false,
    var pescetarian: Boolean? = false,
    var paleo: Boolean? = false,
    var primal: Boolean? = false,
    var whole30: Boolean? = false
)