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
    fun removeFromMealPlan(date: String, time: MealPlanEnum, recipe: Recipe)
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
        val recipe = tmpRecipe ?: return
        val mealPlan = db.mealPlanDao().getMealPlanBlocking(date).firstOrNull() ?: MealPlan().apply {
            this.date = date
        }

        when (time) {
            MealPlanEnum.BREAKFAST -> {
                val tmp = mealPlan.breakfast?.toMutableList() ?: mutableListOf()
                tmp.add(recipe)
                mealPlan.breakfast = tmp
            }
            MealPlanEnum.LUNCH -> {
                val tmp = mealPlan.lunch?.toMutableList() ?: mutableListOf()
                tmp.add(recipe)
                mealPlan.lunch = tmp
            }
            MealPlanEnum.DINNER -> {
                val tmp = mealPlan.dinner?.toMutableList() ?: mutableListOf()
                tmp.add(recipe)
                mealPlan.dinner = tmp
            }
        }

        db.mealPlanDao().saveMealPlan(mealPlan)
        userRepository.updateMealPlans()
    }

    override fun removeFromMealPlan(date: String, time: MealPlanEnum, recipe: Recipe) {
        val mealPlan = db.mealPlanDao().getMealPlanBlocking(date).firstOrNull() ?: MealPlan().apply {
            this.date = date
        }

        when (time) {
            MealPlanEnum.BREAKFAST -> {
                val tmp = mealPlan.breakfast?.toMutableList() ?: mutableListOf()
                mealPlan.breakfast = tmp.filter { it.getId() != recipe.getId() }
            }
            MealPlanEnum.LUNCH -> {
                val tmp = mealPlan.lunch?.toMutableList() ?: mutableListOf()
                mealPlan.lunch = tmp.filter { it.getId() != recipe.getId() }
            }
            MealPlanEnum.DINNER -> {
                val tmp = mealPlan.dinner?.toMutableList() ?: mutableListOf()
                mealPlan.dinner = tmp.filter { it.getId() != recipe.getId() }
            }
        }

        db.mealPlanDao().update(mealPlan)
        userRepository.updateMealPlans()
    }
}