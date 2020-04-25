package com.lonchi.andrej.lonchi_skeleton.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Entity
class User {
    @PrimaryKey var id: Long = -1
    @Json(name = "access_token") var accessToken: String? = ""
    @Embedded(prefix = "data_") var data: UserData? = null
    //todo implementation, this is just a mock for UserRepository display purposes

    data class UserData(var id: Long)
}