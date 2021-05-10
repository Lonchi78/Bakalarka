package com.lonchi.andrej.lonchi_bakalarka.data.converters

import androidx.room.TypeConverter
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class RecipeTypeConverters {
    val moshi = Moshi.Builder().build()

    @TypeConverter
    fun recipesFromString(data: String?): List<Recipe>? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, Recipe::class.java)
        val adapter = moshi.adapter<List<Recipe>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    fun recipesToString(data: List<Recipe>?): String? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, Recipe::class.java)
        val adapter = moshi.adapter<List<Recipe>>(type)
        return adapter.toJson(data)
    }
}