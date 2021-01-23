package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseDao
import com.lonchi.andrej.lonchi_bakalarka.data.entities.FavouriteRecipe


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface FavouriteRecipesDao : BaseDao<FavouriteRecipe> {

    @Query("SELECT * FROM FavouriteRecipe")
    fun getAllRecipes(): LiveData<List<FavouriteRecipe>>

    @Query("SELECT * FROM FavouriteRecipe WHERE uid = :id")
    fun getRecipe(id: String): LiveData<List<FavouriteRecipe>>

    @Query("DELETE FROM FavouriteRecipe WHERE uid = :id")
    fun deleteRecipe(id: String)

    @Query("DELETE FROM FavouriteRecipe")
    fun deleteAllRecipes()

    @Transaction
    fun saveRecipe(recipe: FavouriteRecipe) {
        insert(recipe)
    }

    @Transaction
    fun saveAllRecipes(recipes: List<FavouriteRecipe>) {
        insertAll(recipes)
    }
}