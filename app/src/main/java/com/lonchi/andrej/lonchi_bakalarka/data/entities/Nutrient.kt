package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.NutrientTypeConverters
import com.squareup.moshi.Json

data class NutritionWrapper (
    @TypeConverters(NutrientTypeConverters::class)
    @Json(name = "nutrients") var nutrients: List<Nutrient>? = listOf()
) {
    companion object {
        const val KEY_CALORIES = "Calories"
        const val KEY_FAT = "Fat"
        const val KEY_CARBOHYDRATES = "Carbohydrates"
        const val KEY_PROTEIN = "Protein"
    }

    fun getCalories(): Nutrient? {
        return this.nutrients?.firstOrNull {
            it.name == KEY_CALORIES
        }
    }

    fun getFat(): Nutrient? {
        return this.nutrients?.firstOrNull {
            it.name == KEY_FAT
        }
    }

    fun getCarbohydrates(): Nutrient? {
        return this.nutrients?.firstOrNull {
            it.name == KEY_CARBOHYDRATES
        }
    }

    fun getProtein(): Nutrient? {
        return this.nutrients?.firstOrNull {
            it.name == KEY_PROTEIN
        }
    }
}

data class Nutrient (
    @Json(name = "name") var name: String? = null,
    @Json(name = "title") var title: String? = null,
    @Json(name = "unit") var unit: String? = null,
    @Json(name = "amount") var amount: Float? = null,
    @Json(name = "percentOfDailyNeeds") var percentOfDailyNeeds: Float? = null
)