package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseDao
import com.lonchi.andrej.lonchi_bakalarka.data.entities.User
import io.reactivex.Single


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM User")
    fun listAll(): LiveData<List<User>>

    @Query("SELECT * FROM User")
    fun listAllSingle(): Single<List<User>>

    @Query("DELETE FROM User")
    fun deleteAll()

    @Transaction
    fun saveUser(user: User) {
        deleteAll()
        insert(user)
    }
}