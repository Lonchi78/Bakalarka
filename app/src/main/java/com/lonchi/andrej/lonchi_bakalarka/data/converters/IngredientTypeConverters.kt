package com.lonchi.andrej.lonchi_bakalarka.data.converters

import androidx.room.TypeConverter
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class IngredientTypeConverters {
    val moshi = Moshi.Builder().build()

    @TypeConverter
    fun ingredientsFromString(data: String?): List<Ingredient>? {
        if (data == null) {
            return null
        }
        val type = Types.newParameterizedType(List::class.java, Ingredient::class.java)
        val adapter = moshi.adapter<List<Ingredient>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    fun ingredientsToString(data: List<Ingredient>?): String? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, Ingredient::class.java)
        val adapter = moshi.adapter<List<Ingredient>>(type)
        return adapter.toJson(data)
    }
}