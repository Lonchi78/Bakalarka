package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseDao
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Diets


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface DietsDao : BaseDao<Diets> {

    @Query("SELECT * FROM Diets")
    fun listAll(): LiveData<List<Diets>>

    @Query("DELETE FROM Diets")
    fun deleteAll()

    @Transaction
    fun saveDiets(diets: Diets) {
        deleteAll()
        insert(diets)
    }
}