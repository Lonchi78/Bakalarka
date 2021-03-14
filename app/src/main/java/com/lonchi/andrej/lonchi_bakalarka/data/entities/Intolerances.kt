package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Intolerances (
    @PrimaryKey var id: Long = -1,
    var dairy: Boolean? = false,
    var egg: Boolean? = false,
    var gluten: Boolean? = false,
    var grain: Boolean? = false,
    var peanut: Boolean? = false,
    var seafood: Boolean? = false,
    var sesame: Boolean? = false,
    var shellfish: Boolean? = false,
    var soy: Boolean? = false,
    var sulfite: Boolean? = false,
    var treeNut: Boolean? = false,
    var wheat: Boolean? = false,
)