package com.lonchi.andrej.lonchi_skeleton.data.repository.rest

import com.lonchi.andrej.lonchi_skeleton.data.utils.DeviceTracker
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
data class TestRequestData(var email: String,
                           var password: String,
                           @Json(name = "first_name") var firstname: String? = null,
                           @Json(name = "last_name") var lastname: String? = null,
                           var device: DeviceTracker.DeviceProperties,
                           var role: Int? = null)