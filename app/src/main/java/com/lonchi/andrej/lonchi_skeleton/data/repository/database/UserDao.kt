package com.lonchi.andrej.lonchi_skeleton.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_skeleton.data.base.BaseDao
import com.lonchi.andrej.lonchi_skeleton.data.entities.User


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM User")
    fun listAll(): LiveData<List<User>>

    @Query("DELETE FROM User")
    fun deleteAll()

    @Transaction
    fun saveUser(user: User) {
        deleteAll()
        insert(user)
    }
}