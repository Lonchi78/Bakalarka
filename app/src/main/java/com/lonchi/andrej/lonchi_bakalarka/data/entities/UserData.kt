package com.lonchi.andrej.lonchi_bakalarka.data.entities

data class UserData (
    val name: String? = null,
    val email: String? = null,
    val intolerances: Intolerances? = null,
    val diets: Diets? = null,
    val favouriteRecipes: List<RecipeFavourite>? = null,
    val customRecipes: List<RecipeCustom>? = null
) {

}