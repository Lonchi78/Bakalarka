package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.NutrientTypeConverters
import com.squareup.moshi.Json

data class NutritionWrapper (
    @TypeConverters(NutrientTypeConverters::class)
    @Json(name = "nutrients") var nutrients: List<Nutrient?>? = listOf()
) {
    companion object {
        const val KEY_CALORIES = "Calories"
        const val KEY_CALORIES_UNIT = "cal"

        const val KEY_FAT = "Fat"
        const val KEY_FAT_UNIT = "g"

        const val KEY_CARBOHYDRATES = "Carbohydrates"
        const val KEY_CARBOHYDRATES_UNIT = "g"

        const val KEY_PROTEIN = "Protein"
        const val KEY_PROTEIN_UNIT = "g"

        private const val FAT_DAILY_NEEDS = 70f
        private const val CALORIES_DAILY_NEEDS = 2000f
        private const val CARBOHYDRATES_DAILY_NEEDS = 310f
        private const val PROTEIN_DAILY_NEEDS = 50f

        fun getCaloriesPercentOfDailyNeeds(value: Int): Float = (value / CALORIES_DAILY_NEEDS) * 100
        fun getProteinsPercentOfDailyNeeds(value: Int): Float = (value / PROTEIN_DAILY_NEEDS) * 100
        fun getFatsPercentOfDailyNeeds(value: Int): Float = (value / FAT_DAILY_NEEDS) * 100
        fun getCarbohydratesPercentOfDailyNeeds(value: Int): Float = (value / CARBOHYDRATES_DAILY_NEEDS) * 100
    }

    fun getCalories(): Nutrient? {
        return this.nutrients?.firstOrNull {
            it?.name == KEY_CALORIES
        }
    }

    fun getFat(): Nutrient? {
        return this.nutrients?.firstOrNull {
            it?.name == KEY_FAT
        }
    }

    fun getCarbohydrates(): Nutrient? {
        return this.nutrients?.firstOrNull {
            it?.name == KEY_CARBOHYDRATES
        }
    }

    fun getProtein(): Nutrient? {
        return this.nutrients?.firstOrNull {
            it?.name == KEY_PROTEIN
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