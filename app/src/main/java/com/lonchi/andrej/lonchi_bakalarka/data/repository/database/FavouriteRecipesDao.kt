package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseDao
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeFavourite
import io.reactivex.Single


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface FavouriteRecipesDao : BaseDao<RecipeFavourite> {

    @Query("SELECT * FROM RecipeFavourite")
    fun getAllRecipes(): LiveData<List<RecipeFavourite>>

    @Query("SELECT * FROM RecipeFavourite")
    fun getAllRecipesSingle(): Single<List<RecipeFavourite>>

    @Query("SELECT * FROM RecipeFavourite WHERE uid = :id")
    fun getRecipe(id: String): Single<List<RecipeFavourite>>

    @Query("DELETE FROM RecipeFavourite WHERE uid = :id")
    fun deleteRecipe(id: String)

    @Query("DELETE FROM RecipeFavourite")
    fun deleteAllRecipes()

    @Transaction
    fun saveRecipe(recipeFavourite: RecipeFavourite) {
        insert(recipeFavourite)
    }

    @Transaction
    fun saveAllRecipes(recipeFavourites: List<RecipeFavourite>) {
        insertAll(recipeFavourites)
    }
}