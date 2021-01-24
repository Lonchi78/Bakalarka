package com.lonchi.andrej.lonchi_bakalarka.data.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<T>)

    @Update
    fun update(item: T)

    @Update
    fun updateAll(items: List<T>)

    @Delete
    fun delete(item: T)

    @Delete
    fun deleteAll(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replace(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceAll(items: List<T>)
}