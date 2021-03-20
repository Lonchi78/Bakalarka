package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Intolerances (
    @PrimaryKey var id: Long = -1,
    var intolerances: List<String?>? = null
)