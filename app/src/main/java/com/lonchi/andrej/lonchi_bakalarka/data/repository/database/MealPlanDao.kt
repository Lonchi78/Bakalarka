package com.lonchi.andrej.lonchi_bakalarka.data.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseDao
import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlan
import io.reactivex.Single


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
@Dao
interface MealPlanDao : BaseDao<MealPlan> {

    @Query("SELECT * FROM MealPlan")
    fun getAllMealPlans(): LiveData<List<MealPlan>>

    @Query("SELECT * FROM MealPlan")
    fun getAllMealPlansBlocking(): List<MealPlan>

    @Query("SELECT * FROM MealPlan")
    fun getAllMealPlanSingle(): Single<List<MealPlan>>

    @Query("SELECT * FROM MealPlan WHERE date = :date")
    fun getMealPlan(date: String): Single<List<MealPlan>>

    @Query("SELECT * FROM MealPlan WHERE date = :date")
    fun getMealPlanBlocking(date: String): List<MealPlan>

    @Query("DELETE FROM MealPlan")
    fun deleteAllMealPlans()

    @Transaction
    fun saveMealPlan(mealPlan: MealPlan) {
        insert(mealPlan)
    }

    @Transaction
    fun saveAllMealPlan(mealPlans: List<MealPlan>) {
        insertAll(mealPlans)
    }
}