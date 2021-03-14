package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseDao
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Intolerances


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface IntolerancesDao : BaseDao<Intolerances> {

    @Query("SELECT * FROM Intolerances")
    fun listAll(): LiveData<List<Intolerances>>

    @Query("DELETE FROM Intolerances")
    fun deleteAll()

    @Transaction
    fun saveIntolerances(intolerances: Intolerances) {
        deleteAll()
        insert(intolerances)
    }
}