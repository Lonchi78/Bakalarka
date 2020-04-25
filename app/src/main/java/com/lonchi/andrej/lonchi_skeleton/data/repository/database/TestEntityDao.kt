package com.lonchi.andrej.lonchi_skeleton.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lonchi.andrej.lonchi_skeleton.data.base.BaseDao
import com.lonchi.andrej.lonchi_skeleton.data.entities.TestEntity
import io.reactivex.Single


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface TestEntityDao : BaseDao<TestEntity> {

    @Query("SELECT * FROM TestEntity")
    fun listAll(): LiveData<List<TestEntity>>

    @Query("SELECT * FROM TestEntity")
    fun listAllSingle(): Single<List<TestEntity>>

    @Query("DELETE FROM TestEntity")
    fun deleteAll()

    @Query("SELECT * FROM TestEntity WHERE id = :id")
    fun getTestEntity(id: Int): LiveData<TestEntity>
}