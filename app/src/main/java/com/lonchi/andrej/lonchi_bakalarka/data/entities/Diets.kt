package com.lonchi.andrej.lonchi_bakalarka.data.entities

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lonchi.andrej.lonchi_bakalarka.ui.diets.adapter.AddDietsObject

@Entity
data class Diets (
    @PrimaryKey var id: Long = -1,
    var diets: List<String?>? = null
)