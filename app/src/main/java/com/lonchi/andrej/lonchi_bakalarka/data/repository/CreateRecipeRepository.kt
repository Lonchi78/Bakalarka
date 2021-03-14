package com.lonchi.andrej.lonchi_bakalarka.data.repository

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.entities.*
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.utils.DeviceTracker
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface CreateRecipeRepository {
    val newRecipe: MutableLiveData<Resource<RecipeCustom>>
    val diets: MutableLiveData<List<String>>

    fun createNewRecipe()

    fun setRecipeName(name: String)

    fun setRecipeCookingTime(hours: Int, minutes: Int)

    fun addRecipeIngredient(ingredientText: String)

    fun addRecipeInstruction(instructionText: String)
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

    override val newRecipe: MutableLiveData<Resource<RecipeCustom>> = MutableLiveData<Resource<RecipeCustom>>().apply {
        postValue(
            Resource.success(
                DietsEnum.PESCETARIAN
            )
        )
    }

    override fun createNewRecipe() {
        newRecipe.postValue(Resource.success(RecipeCustom()))
    }

    override fun setRecipeName(name: String) {
        val currentRecipe = newRecipe.value?.data
        currentRecipe?.apply { this.title = name }
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun setRecipeCookingTime(hours: Int, minutes: Int) {
        val currentRecipe = newRecipe.value?.data

        //  Calculate total cooking time in minutes and save it
        val totalMinutes = TimeUnit.HOURS.toMinutes(hours.toLong()).toInt() + minutes
        currentRecipe?.apply { this.readyInMinutes = totalMinutes }
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun addRecipeIngredient(ingredientText: String) {
        val currentRecipe = newRecipe.value?.data
        val currentIngredients = currentRecipe?.getAllIngredients()?.toMutableList()

        currentIngredients?.add(
            Ingredient(

            )
        )

        currentRecipe?.apply {
            this.ingredients = currentIngredients
        }
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun addRecipeInstruction(instructionText: String) {
        val currentRecipe = newRecipe.value?.data
        val currentInstructionWrapper = currentRecipe?.instructions?.firstOrNull() ?: InstructionsWrapper()
        val currentInstructions = currentInstructionWrapper.steps?.toMutableList() ?: mutableListOf()

        //  Get instruction number
        var newInstructionNumber = currentInstructions.maxByOrNull { it.number ?: 0 }?.number
        if (newInstructionNumber == null) newInstructionNumber = 1 else newInstructionNumber += 1

        //  Create instruction
        currentInstructions.add(
            Instruction(
                number = newInstructionNumber,
                step = instructionText
            )
        )

        //  Add instruction to other ones
        currentInstructionWrapper.steps = currentInstructions
        currentRecipe?.apply {
            this.instructions = listOf(currentInstructionWrapper)
        }
        newRecipe.postValue(Resource.success(currentRecipe))
    }
}