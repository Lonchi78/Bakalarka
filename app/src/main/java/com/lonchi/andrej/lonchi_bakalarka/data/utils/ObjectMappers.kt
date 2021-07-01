package com.lonchi.andrej.lonchi_bakalarka.data.utils

import com.google.firebase.auth.FirebaseUser
import com.lonchi.andrej.lonchi_bakalarka.data.entities.User

fun FirebaseUser.mapToUser(): User {
    return User().apply {
        name = this@mapToUser.displayName
        email = this@mapToUser.email
        emailVerified = this@mapToUser.isEmailVerified
        photoUrl = this@mapToUser.photoUrl?.toString()
        uid = this@mapToUser.uid
        providerId = this@mapToUser.providerId
        phoneNumber = this@mapToUser.phoneNumber
    }
}