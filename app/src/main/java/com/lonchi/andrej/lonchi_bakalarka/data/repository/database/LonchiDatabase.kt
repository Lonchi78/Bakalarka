package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lonchi.andrej.lonchi_bakalarka.data.converters.*
import com.lonchi.andrej.lonchi_bakalarka.data.entities.*


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Database(
        entities = [
            TestEntity::class,
            RecipeFavourite::class,
            RecipeCustom::class,
            Intolerances::class,
            Filter::class,
            Diets::class,
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
    abstract fun intolerancesDao(): IntolerancesDao
    abstract fun filterDao(): FilterDao
    abstract fun dietsDao(): DietsDao
}