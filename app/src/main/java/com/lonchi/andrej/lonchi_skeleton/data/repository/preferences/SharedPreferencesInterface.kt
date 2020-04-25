package com.lonchi.andrej.lonchi_skeleton.data.repository.preferences

import androidx.lifecycle.LiveData
import com.lonchi.andrej.lonchi_skeleton.data.utils.SharedPreferenceLiveData


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface SharedPreferencesInterface {
    fun getAccessTokenLiveData(): LiveData<SharedPreferenceLiveData.State<String>>

    fun getAccessTokenFromSharedPreferences(): String

    fun getFirebaseTokenFromSharedPreferences(): String

    fun putAccessTokenToSharedPreferences(token: String?)

    fun putFirebaseTokenToSharedPreferences(token: String?)

    fun removeAccessToken()

    fun getLastKnownLanguage(): String

    fun updateLastKnownLanguage(lang: String)
}
