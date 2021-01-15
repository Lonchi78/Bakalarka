package com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SharedPreferenceLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.utils.stringLiveData
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Singleton
class SharedPreferencesImpl @Inject internal constructor(
    private val prefs: SharedPreferences
) : SharedPreferencesInterface {

    companion object Keys {
        const val KEY_AUTH_TOKEN = "auth.token"
        const val KEY_LANG_KEY = "language"
        const val KEY_FIREBASE_TOKEN = "firebase.token"
        const val KEY_DEVICE_HW_ID = "device.hw.id"
        const val KEY_DEVICE_ID = "device.id"
        const val KEY_STATUS_WORK_NOW = "status.work.now"
        const val KEY_STATUS_WORK_LATER = "status.work.later"
    }

    // Temporary fix for issue with SharePreference LiveData
    private val token: SharedPreferenceLiveData<String> by lazy {
        prefs.stringLiveData(
            KEY_AUTH_TOKEN,
            ""
        )
    }

    private fun removePreference(key: String) {
        prefs.edit().remove(key).apply()
    }

    private fun getBooleanFromSharedPreferences(key: String, defaultValue: Boolean): Boolean =
        prefs.getBoolean(key, defaultValue)

    private fun getStringFromSharedPreferences(key: String, defaultValue: String): String =
        prefs.getString(key, defaultValue) ?: ""

    private fun getIntFromSharedPreferences(key: String, defaultValue: Int): Int =
        prefs.getInt(key, defaultValue)

    private fun getLongFromSharedPreferences(key: String, defaultValue: Long): Long =
        prefs.getLong(key, defaultValue)

    private fun putBooleanToSharedPreferences(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    private fun putStringToSharedPreferences(key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    private fun putIntToSharedPreferences(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    private fun putLongToSharedPreferences(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    override fun getAccessTokenLiveData(): LiveData<SharedPreferenceLiveData.State<String>> = token

    override fun getUserUidFromSharedPreferences(): String =
        getStringFromSharedPreferences(KEY_AUTH_TOKEN, "")

    override fun getFirebaseTokenFromSharedPreferences(): String =
        getStringFromSharedPreferences(KEY_FIREBASE_TOKEN, "")

    override fun putUserUidToSharedPreferences(token: String?) =
        putStringToSharedPreferences(KEY_AUTH_TOKEN, token)

    override fun putFirebaseTokenToSharedPreferences(token: String?) =
        putStringToSharedPreferences(KEY_FIREBASE_TOKEN, token)

    override fun removeUserId() = removePreference(KEY_AUTH_TOKEN)

    override fun getLastKnownLanguage(): String =
        getStringFromSharedPreferences(KEY_LANG_KEY, "")

    override fun updateLastKnownLanguage(lang: String) =
        putStringToSharedPreferences(KEY_LANG_KEY, lang)

}
