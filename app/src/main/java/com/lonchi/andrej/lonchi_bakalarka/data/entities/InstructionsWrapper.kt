package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.InstructionTypeConverters
import com.squareup.moshi.Json

data class InstructionsWrapper (
    @Json(name = "name") var name: String? = null,
    @Json(name = "steps") @TypeConverters(InstructionTypeConverters::class) var steps: List<Instruction>? = listOf()
)

data class Instruction (
    @Json(name = "number") var number: Int? = null,
    @Json(name = "step") var step: String? = null,
    @Json(name = "length") @Embedded(prefix = "instruction_") var length: InstructionLength? = null
)

data class InstructionLength (
    @Json(name = "number") var number: Int? = null,
    @Json(name = "unit") var unit: String? = null
)