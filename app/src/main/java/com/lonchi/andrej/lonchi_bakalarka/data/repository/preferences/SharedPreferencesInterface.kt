package com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences

import androidx.lifecycle.LiveData
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SharedPreferenceLiveData


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface SharedPreferencesInterface {
    fun getAccessTokenLiveData(): LiveData<SharedPreferenceLiveData.State<String>>

    fun getUserUidFromSharedPreferences(): String

    fun getFirebaseTokenFromSharedPreferences(): String

    fun putUserUidToSharedPreferences(token: String?)

    fun putFirebaseTokenToSharedPreferences(token: String?)

    fun removeUserId()

    fun getLastKnownLanguage(): String

    fun updateLastKnownLanguage(lang: String)

    fun getFirstStart(): Boolean

    fun updateFirstStart(value: Boolean)
}
