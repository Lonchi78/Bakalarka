package com.lonchi.andrej.lonchi_bakalarka.data.entities

import android.content.Context
import com.lonchi.andrej.lonchi_bakalarka.R

enum class IntolerancesEnum (val stringRes: Int) {
    DAIRY(R.string.intolerances_dairy),
    EGG(R.string.intolerances_egg),
    GLUTEN(R.string.intolerances_gluten),
    GRAIN(R.string.intolerances_grain),
    PEANUT(R.string.intolerances_peanut),
    SEAFOOD(R.string.intolerances_seafood),
    SESAME(R.string.intolerances_sesame),
    SHELLFISH(R.string.intolerances_shellfish),
    SOY(R.string.intolerances_soy),
    SULFITE(R.string.intolerances_sulfite),
    TREE_NUT(R.string.intolerances_tree_nut),
    WHEAT(R.string.intolerances_wheat);

    companion object {

        fun getAllIntolerances(context: Context): List<String> {
            val tmp = mutableListOf<String>()
            values().forEach {
                tmp.add(context.getString(it.stringRes))
            }
            return tmp
        }

    }
}