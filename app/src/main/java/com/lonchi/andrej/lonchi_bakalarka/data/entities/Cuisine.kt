package com.lonchi.andrej.lonchi_bakalarka.data.entities

import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
data class Cuisine (
    @Json(name = "todo") var name: String = ""
)