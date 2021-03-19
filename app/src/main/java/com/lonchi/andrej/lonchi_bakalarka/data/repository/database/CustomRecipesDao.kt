package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseDao
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeCustom
import io.reactivex.Single


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface CustomRecipesDao : BaseDao<RecipeCustom> {

    @Query("SELECT * FROM RecipeCustom")
    fun getAllRecipes(): LiveData<List<RecipeCustom>>

    @Query("SELECT * FROM RecipeCustom")
    fun getAllRecipesSingle(): Single<List<RecipeCustom>>

    @Query("SELECT * FROM RecipeCustom WHERE uid = :id")
    fun getRecipe(id: String): Single<List<RecipeCustom>>

    @Query("DELETE FROM RecipeCustom")
    fun deleteAllRecipes()

    @Transaction
    fun saveRecipe(recipeCustom: RecipeCustom) {
        insert(recipeCustom)
    }

    @Transaction
    fun saveAllRecipes(recipeCustoms: List<RecipeCustom>) {
        insertAll(recipeCustoms)
    }
}