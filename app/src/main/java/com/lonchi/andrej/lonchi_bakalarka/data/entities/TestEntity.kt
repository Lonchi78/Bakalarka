package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Entity
class TestEntity {

    @PrimaryKey var id: Long = -1
    @Json(name = "access_token") var accessToken: String? = ""
    @Embedded(prefix = "coach_") var data: TestData? = null

    data class TestData(var id: Long)
}