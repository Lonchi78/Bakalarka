package com.lonchi.andrej.lonchi_skeleton.data.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lonchi.andrej.lonchi_skeleton.data.entities.TestEntity
import com.lonchi.andrej.lonchi_skeleton.data.entities.User


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Database(
        entities = [
            TestEntity::class,
            User::class
        ],
        version = 1,
        exportSchema = false
)
abstract class LonchiDatabase : RoomDatabase() {

    abstract fun testEntityDao(): TestEntityDao
    abstract fun userDao(): UserDao
}