package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.*
import com.lonchi.andrej.lonchi_bakalarka.data.entities.CustomRecipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.FavouriteRecipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.TestEntity
import com.lonchi.andrej.lonchi_bakalarka.data.entities.User


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Database(
        entities = [
            TestEntity::class,
            FavouriteRecipe::class,
            CustomRecipe::class,
            User::class
        ],
        version = 1,
        exportSchema = false
)

@TypeConverters(
    InstructionTypeConverters::class,
    NutrientTypeConverters::class,
    InstructionsWrapperTypeConverters::class,
    IngredientTypeConverters::class,
    ListOfStringsTypeConverters::class
)

abstract class LonchiDatabase : RoomDatabase() {

    abstract fun testEntityDao(): TestEntityDao
    abstract fun userDao(): UserDao
    abstract fun favouriteRecipesDao(): FavouriteRecipesDao
    abstract fun customRecipesDao(): CustomRecipesDao
}