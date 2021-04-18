package com.lonchi.andrej.lonchi_bakalarka.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlan
import com.lonchi.andrej.lonchi_bakalarka.data.entities.MealPlanEnum
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.utils.DeviceTracker
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import io.reactivex.Single
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface MealPlanRepository {
    val loggedMealPlan: LiveData<Resource<MealPlan>>
    var tmpRecipe: Recipe?

    fun getAllMealPlans(): LiveData<List<MealPlan>>
    fun getMealPlan(date: String): Single<List<MealPlan>>

    fun saveToMealPlan(date: String, time: MealPlanEnum)
}

class MealPlanRepositoryImpl @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    private val prefs: SharedPreferencesInterface,
    retrofit: Retrofit,
    private val deviceTracker: DeviceTracker,
    private val userRepository: UserRepository
) : BaseRepository(db, api, prefs, retrofit), MealPlanRepository {

    override var tmpRecipe: Recipe? = null

    override val loggedMealPlan: LiveData<Resource<MealPlan>> = Transformations.map(db.mealPlanDao().getAllMealPlans()) {
        it.firstOrNull()?.let { Resource.success(it) }
            ?: Resource.error(ErrorIdentification.Authentication(), null)
    }

    override fun getAllMealPlans(): LiveData<List<MealPlan>> {
        return  db.mealPlanDao().getAllMealPlans()
    }

    override fun getMealPlan(date: String): Single<List<MealPlan>> {
        return db.mealPlanDao().getMealPlan(date)
    }

    override fun saveToMealPlan(date: String, time: MealPlanEnum) {
        Timber.d("saveToMealPlan: $date, time = $time")
        Timber.d("saveToMealPlan tmpRecipe: ${tmpRecipe.toString()}")
        val recipe = tmpRecipe ?: return
        val mealPlan = db.mealPlanDao().getMealPlanBlocking(date).firstOrNull() ?: MealPlan().apply {
            this.date = date
        }
        Timber.d("saveToMealPlan: mealPlan = $mealPlan")
        when (time) {
            MealPlanEnum.BREAKFAST -> {
                val tmp = mealPlan.breakfast?.toMutableList() ?: mutableListOf()
                Timber.d("saveToMealPlan BREAKFAST: ${tmp.size}")
                tmp.add(recipe)
                mealPlan.breakfast = tmp
                Timber.d("saveToMealPlan BREAKFAST: ${mealPlan.breakfast?.size}")
            }
            MealPlanEnum.LUNCH -> {
                val tmp = mealPlan.lunch?.toMutableList() ?: mutableListOf()
                Timber.d("saveToMealPlan LUNCH: ${tmp.size}")
                tmp.add(recipe)
                mealPlan.lunch = tmp
                Timber.d("saveToMealPlan LUNCH: ${mealPlan.lunch?.size}")
            }
            MealPlanEnum.DINNER -> {
                val tmp = mealPlan.dinner?.toMutableList() ?: mutableListOf()
                Timber.d("saveToMealPlan DINNER: ${tmp.size}")
                tmp.add(recipe)
                mealPlan.dinner = tmp
                Timber.d("saveToMealPlan DINNER: ${mealPlan.dinner?.size}")
            }
        }
        db.mealPlanDao().saveMealPlan(mealPlan)
    }
}