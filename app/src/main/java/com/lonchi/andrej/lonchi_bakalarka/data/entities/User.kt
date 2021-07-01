package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Entity
class User {
    @PrimaryKey var id: Long = -1
    var name: String? = ""
    var email: String? = ""
    var emailVerified: Boolean = false
    var photoUrl: String? = null
    var uid: String? = ""
    var phoneNumber: String? = ""
    var providerId: String? = ""
}