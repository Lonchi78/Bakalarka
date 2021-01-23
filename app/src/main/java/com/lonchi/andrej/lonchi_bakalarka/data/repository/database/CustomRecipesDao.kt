package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseDao
import com.lonchi.andrej.lonchi_bakalarka.data.entities.CustomRecipe


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface CustomRecipesDao : BaseDao<CustomRecipe> {

    @Query("SELECT * FROM CustomRecipe")
    fun getAllRecipes(): LiveData<List<CustomRecipe>>

    @Query("SELECT * FROM CustomRecipe WHERE id = :id")
    fun getRecipe(id: String): LiveData<List<CustomRecipe>>

    @Query("DELETE FROM CustomRecipe")
    fun deleteAllRecipes()

    @Transaction
    fun saveRecipe(recipe: CustomRecipe) {
        insert(recipe)
    }

    @Transaction
    fun saveAllRecipes(recipes: List<CustomRecipe>) {
        insertAll(recipes)
    }
}