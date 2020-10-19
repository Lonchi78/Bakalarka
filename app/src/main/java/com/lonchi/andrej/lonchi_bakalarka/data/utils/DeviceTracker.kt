package com.lonchi.andrej.lonchi_bakalarka.data.utils

import com.squareup.moshi.Json

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface DeviceTracker {
    open val OS_TYPE: String
        get() = "Android"
    open val CATEGORY_TABLET: String
        get() = "tablet"
    open val CATEGORY_PHONE: String
        get() = "phone"

    fun getDeviceProperties(): DeviceProperties

    class DeviceProperties(
            @Json(name = "id_firebase") val firebaseToken: String?,
            @Json(name = "os_type") val osType: String,
            @Json(name = "os_version") val osVersion: String,
            @Json(name = "device_brand") val deviceBrand: String,
            @Json(name = "device_model") val deviceModel: String,
            @Json(name = "device_category") val deviceCategory: String,
            @Json(name = "system_language") val systemLanguage: String)

    class DeviceLocation(val lat: Double, val long: Double)
}
