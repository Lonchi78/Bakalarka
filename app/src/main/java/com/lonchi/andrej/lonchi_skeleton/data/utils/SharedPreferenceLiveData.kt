package com.lonchi.andrej.lonchi_skeleton.data.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
abstract class SharedPreferenceLiveData<T>(private val sharedPrefs: SharedPreferences,
                                           private val key: String,
                                           private val defValue: T) : LiveData<SharedPreferenceLiveData.State<T>>() {
    data class State<T>(val defined: Boolean, val value: T)

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        if (key == this.key) {
            value = State(
                isValueDefined(
                    sharedPreferences,
                    key
                ), getSavedValue(sharedPreferences, key, defValue)
            )
        }
    }

    private fun isValueDefined(sharedPrefs: SharedPreferences, key: String) = sharedPrefs.contains(key)

    protected abstract fun getSavedValue(sharedPrefs: SharedPreferences, key: String, defValue: T): T

    override fun onActive() {
        super.onActive()
        value = State(
            isValueDefined(sharedPrefs, key),
            getSavedValue(sharedPrefs, key, defValue)
        )
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}

fun SharedPreferences.intLiveData(key: String, defValue: Int) =
        object : SharedPreferenceLiveData<Int>(this, key, defValue) {
            override fun getSavedValue(sharedPrefs: SharedPreferences, key: String, defValue: Int) = sharedPrefs.getInt(key, defValue)
        }


fun SharedPreferences.stringLiveData(key: String, defValue: String) =
        object : SharedPreferenceLiveData<String>(this, key, defValue) {
            override fun getSavedValue(sharedPrefs: SharedPreferences, key: String, defValue: String) = sharedPrefs.getString(key, defValue) ?: defValue
        }


fun SharedPreferences.booleanLiveData(key: String, defValue: Boolean) =
        object : SharedPreferenceLiveData<Boolean>(this, key, defValue) {
            override fun getSavedValue(sharedPrefs: SharedPreferences, key: String, defValue: Boolean) = sharedPrefs.getBoolean(key, defValue)
        }

fun SharedPreferences.floatLiveData(key: String, defValue: Float) =
        object : SharedPreferenceLiveData<Float>(this, key, defValue) {
            override fun getSavedValue(sharedPrefs: SharedPreferences, key: String, defValue: Float) = sharedPrefs.getFloat(key, defValue)
        }

fun SharedPreferences.longLiveData(key: String, defValue: Long) =
        object : SharedPreferenceLiveData<Long>(this, key, defValue) {
            override fun getSavedValue(sharedPrefs: SharedPreferences, key: String, defValue: Long) = sharedPrefs.getLong(key, defValue)
        }

fun SharedPreferences.stringSetLiveData(key: String, defValue: Set<String>) =
        object : SharedPreferenceLiveData<Set<String>>(this, key, defValue) {
            override fun getSavedValue(sharedPrefs: SharedPreferences, key: String, defValue: Set<String>) = sharedPrefs.getStringSet(key, defValue) ?: defValue
        }