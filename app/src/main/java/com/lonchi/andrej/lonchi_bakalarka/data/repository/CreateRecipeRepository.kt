package com.lonchi.andrej.lonchi_bakalarka.data.repository

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Instruction
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeCustom
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.utils.DeviceTracker
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface CreateRecipeRepository {
    val newRecipe: MutableLiveData<Resource<RecipeCustom>>

    fun createNewRecipe()

    fun setRecipeName(name: String)

    fun setRecipeCookingTime(hours: Int, minutes: Int)

    fun addRecipeIngredient(ingredient: Ingredient)

    fun addRecipeInstruction(instruction: Instruction)
}

class CreateRecipeRepositoryImpl @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    private val prefs: SharedPreferencesInterface,
    retrofit: Retrofit,
    private val deviceTracker: DeviceTracker
) : BaseRepository(db, api, prefs, retrofit), CreateRecipeRepository {


    override val newRecipe: MutableLiveData<Resource<RecipeCustom>> = MutableLiveData<Resource<RecipeCustom>>().apply {
        postValue(Resource.notStarted())
    }

    override fun createNewRecipe() {
        newRecipe.postValue(Resource.success(RecipeCustom()))
    }

    override fun setRecipeName(name: String) {
        val currentRecipe = newRecipe.value?.data
        currentRecipe?.apply {
            this.title = name
        }
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun setRecipeCookingTime(hours: Int, minutes: Int) {
        val totalMinutes = TimeUnit.HOURS.toMinutes(hours.toLong()).toInt() + minutes
        val currentRecipe = newRecipe.value?.data
        currentRecipe?.apply {
            this.readyInMinutes = totalMinutes
        }
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun addRecipeIngredient(ingredient: Ingredient) {
        val currentRecipe = newRecipe.value?.data
        val currentIngredients = currentRecipe?.getAllIngredients()?.toMutableList()
        currentIngredients?.add(ingredient)
        currentRecipe?.apply {
            this.ingredients = currentIngredients
        }
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun addRecipeInstruction(instruction: Instruction) {
        val currentRecipe = newRecipe.value?.data
        val currentInstructions = currentRecipe?.getAllInstructions()?.toMutableList()
        currentInstructions?.add(instruction)
        currentRecipe?.apply {
            this.instructions?.firstOrNull()?.steps = currentInstructions
        }
        newRecipe.postValue(Resource.success(currentRecipe))
    }
}