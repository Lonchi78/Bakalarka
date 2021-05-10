package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Filter (
    @PrimaryKey var id: Long = -1,
    var includeDiets: Boolean = false,
    var includeIntolerances: Boolean = false,
    var caloriesMin: Int = 0,
    var caloriesMax: Int = 800,
    var proteinMin: Int = 0,
    var proteinMax: Int = 100,
    var fatMin: Int = 0,
    var fatMax: Int = 100,
    var carbsMin: Int = 0,
    var carbsMax: Int = 100,
)