package com.lonchi.andrej.lonchi_bakalarka.data.converters

import androidx.room.TypeConverter
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Instruction
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class InstructionTypeConverters {
    val moshi = Moshi.Builder().build()

    @TypeConverter
    fun instructionFromString(data: String?): List<Instruction>? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, Instruction::class.java)
        val adapter = moshi.adapter<List<Instruction>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    fun instructionToString(data: List<Instruction>?): String? {
        if (data == null) return null
        val type = Types.newParameterizedType(List::class.java, Instruction::class.java)
        val adapter = moshi.adapter<List<Instruction>>(type)
        return adapter.toJson(data)
    }
}