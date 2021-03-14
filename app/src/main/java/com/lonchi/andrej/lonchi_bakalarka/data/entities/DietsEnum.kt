package com.lonchi.andrej.lonchi_bakalarka.data.entities

import android.content.Context
import com.lonchi.andrej.lonchi_bakalarka.R

enum class DietsEnum (val stringRes: Int) {
    GLUTEN_FREE(R.string.diet_gluten_free),
    KETOGENIC(R.string.diet_ketogenic),
    VEGETARIAN(R.string.diet_vegetarian),
    LACTO_VEGETARIAN(R.string.diet_lacto_vegetarian),
    OVO_VEGETARIAN(R.string.diet_ovo_vegetarian),
    VEGAN(R.string.diet_vegan),
    PESCETARIAN(R.string.diet_pescetarian),
    PALEO(R.string.diet_paleo),
    PRIMAL(R.string.diet_primal),
    WHOLE_30(R.string.diet_whole_30);

    companion object {

        fun getAllDiets(context: Context): List<String> {
            val tmp = mutableListOf<String>()
            values().forEach {
                tmp.add(context.getString(it.stringRes))
            }
            return tmp
        }

    }
}