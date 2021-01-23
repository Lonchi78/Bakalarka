package com.lonchi.andrej.lonchi_bakalarka.data.converters

import androidx.room.TypeConverter
import com.lonchi.andrej.lonchi_bakalarka.data.entities.InstructionsWrapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class InstructionsWrapperTypeConverters {
    val moshi = Moshi.Builder().build()

    @TypeConverter
    fun instructionsWrapperFromString(data: String?): List<InstructionsWrapper>? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, InstructionsWrapper::class.java)
        val adapter = moshi.adapter<List<InstructionsWrapper>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    fun instructionsWrapperToString(data: List<InstructionsWrapper>?): String? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, InstructionsWrapper::class.java)
        val adapter = moshi.adapter<List<InstructionsWrapper>>(type)
        return adapter.toJson(data)
    }
}