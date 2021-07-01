package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail

enum class RecipeIdTypeEnum {
    FAVOURITE_RECIPE,
    OWN_RECIPE,
    REST;

    companion object {
        fun getRecipeIdType(type: Int): RecipeIdTypeEnum {
            return when (type) {
                FAVOURITE_RECIPE.ordinal -> FAVOURITE_RECIPE
                OWN_RECIPE.ordinal -> OWN_RECIPE
                else -> REST
            }
        }
    }
}