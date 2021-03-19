package com.lonchi.andrej.lonchi_bakalarka.data.entities

data class UserData (
    val name: String,
    val email: String,
    val intolerances: Intolerances,
    val diets: Diets,
    val favouriteRecipes: List<RecipeFavourite>,
    val customRecipes: List<RecipeCustom>
)