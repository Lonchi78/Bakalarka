package com.lonchi.andrej.lonchi_bakalarka.data.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class ListOfStringsTypeConverters {
    val moshi = Moshi.Builder().build()

    @TypeConverter
    fun listOfStringsFromString(data: String?): List<String>? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    fun listOfStringsToString(data: List<String>?): String? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.toJson(data)
    }
}