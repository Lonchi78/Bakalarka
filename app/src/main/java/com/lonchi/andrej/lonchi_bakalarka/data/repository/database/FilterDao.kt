package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseDao
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Filter
import io.reactivex.Single


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface FilterDao : BaseDao<Filter> {

    @Query("SELECT * FROM Filter")
    fun listAll(): LiveData<List<Filter>>

    @Query("SELECT * FROM Filter")
    fun listAllSingle(): Single<List<Filter>>

    @Query("DELETE FROM Filter")
    fun deleteAll()

    @Transaction
    fun saveFilter(filter: Filter) {
        deleteAll()
        insert(filter)
    }
}