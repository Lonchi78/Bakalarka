package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Embedded
import com.squareup.moshi.Json

data class Ingredient (
    @Json(name = "id") var id: Long? = -1,
    @Json(name = "image") var image: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "originalName") var originalName: String? = null,
    @Json(name = "customMeasure") var customMeasure: String? = null,
    @Json(name = "measures") @Embedded(prefix = "ingredient_") var measures: Measures? = null
)

data class Measures (
    @Json(name = "us") @Embedded(prefix = "measure_") var us: MeasureData? = null,
    @Json(name = "metric") @Embedded(prefix = "measure_") var metric: MeasureData? = null
)

data class MeasureData (
    @Json(name = "amount") var amount: Float? = null,
    @Json(name = "unitShort") var unitShort: String? = null,
    @Json(name = "unitLong") var unitLong: String? = null
)