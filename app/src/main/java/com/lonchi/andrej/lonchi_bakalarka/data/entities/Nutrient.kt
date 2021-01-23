package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.NutrientTypeConverters
import com.squareup.moshi.Json

data class NutritionWrapper (
    @Json(name = "nutrients") @TypeConverters(NutrientTypeConverters::class) var nutrients: List<Nutrient>? = listOf()
)

data class Nutrient (
    @Json(name = "name") var name: String? = null,
    @Json(name = "title") var title: String? = null,
    @Json(name = "unit") var unit: String? = null,
    @Json(name = "amount") var amount: Float? = null,
    @Json(name = "percentOfDailyNeeds") var percentOfDailyNeeds: Float? = null
)