package com.lonchi.andrej.lonchi_bakalarka.data.converters

import androidx.room.TypeConverter
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Nutrient
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class NutrientTypeConverters {
    val moshi = Moshi.Builder().build()

    @TypeConverter
    fun nutrientFromString(data: String?): List<Nutrient>? {
        if (data == null) {
            return null
        }
        val type = Types.newParameterizedType(List::class.java, Nutrient::class.java)
        val adapter = moshi.adapter<List<Nutrient>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    fun nutrientToString(data: List<Nutrient>?): String? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, Nutrient::class.java)
        val adapter = moshi.adapter<List<Nutrient>>(type)
        return adapter.toJson(data)
    }
}