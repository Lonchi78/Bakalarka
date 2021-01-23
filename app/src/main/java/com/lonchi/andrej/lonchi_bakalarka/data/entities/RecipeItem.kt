package com.lonchi.andrej.lonchi_bakalarka.data.entities

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.IngredientTypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.InstructionsWrapperTypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.ListOfStringsTypeConverters
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface RecipeItem {

    fun getId(): String

    fun getIdType(): RecipeIdTypeEnum

    fun getName(): String

    fun getImageUrl(): String

    fun getCookingTime(): Int

    fun getNumberOfIngredients(): Int

}