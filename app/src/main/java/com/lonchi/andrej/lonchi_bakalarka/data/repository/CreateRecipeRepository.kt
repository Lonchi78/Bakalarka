package com.lonchi.andrej.lonchi_bakalarka.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.entities.*
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.KEY_CALORIES
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.KEY_CALORIES_UNIT
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.KEY_CARBOHYDRATES
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.KEY_CARBOHYDRATES_UNIT
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.KEY_FAT
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.KEY_FAT_UNIT
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.KEY_PROTEIN
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.KEY_PROTEIN_UNIT
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.getCaloriesPercentOfDailyNeeds
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.getCarbohydratesPercentOfDailyNeeds
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.getFatsPercentOfDailyNeeds
import com.lonchi.andrej.lonchi_bakalarka.data.entities.NutritionWrapper.Companion.getProteinsPercentOfDailyNeeds
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import retrofit2.Retrofit
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface CreateRecipeRepository {
    val newRecipe: MutableLiveData<Resource<RecipeCustom>>

    val allDiets: MutableLiveData<List<String>>

    val allIntolerances: MutableLiveData<List<String>>

    fun createNewRecipe()

    fun deleteRecipe(recipeId: String)

    fun setRecipeName(name: String)

    fun setRecipeCookingTime(hours: Int, minutes: Int)

    fun addRecipeIngredient(ingredientName: String, ingredientMesaure: String)

    fun deleteIngredient(ingredient: Ingredient)

    fun addRecipeInstruction(instructionText: String)

    fun addNutrition(calories: Int, fat: Int, protein: Int, carbs: Int)

    fun addDiets(diets: List<String?>)

    fun addIntolerances(intolerances: List<String?>)

    fun saveNewRecipe()
}

class CreateRecipeRepositoryImpl @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    private val prefs: SharedPreferencesInterface,
    retrofit: Retrofit,
    private val context: Context,
    private val userRepository: UserRepository
) : BaseRepository(db, api, prefs, retrofit), CreateRecipeRepository {

    override val newRecipe: MutableLiveData<Resource<RecipeCustom>> = MutableLiveData<Resource<RecipeCustom>>().apply {
        postValue(Resource.notStarted())
    }

    override val allDiets: MutableLiveData<List<String>> = MutableLiveData<List<String>>().apply {
        postValue(DietsEnum.getAllDiets(context))
    }

    override val allIntolerances: MutableLiveData<List<String>> = MutableLiveData<List<String>>().apply {
        postValue(IntolerancesEnum.getAllIntolerances(context))
    }

    override fun createNewRecipe() {
        newRecipe.postValue(Resource.success(RecipeCustom()))
    }

    override fun deleteRecipe(recipeId: String) {
        db.customRecipesDao().deleteRecipe(recipeId)
        userRepository.updateCustomRecipes()
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

    override fun addRecipeIngredient(ingredientName: String, ingredientMesaure: String) {
        val currentRecipe = newRecipe.value?.data
        val currentIngredients = currentRecipe?.getAllIngredients()?.toMutableList()

        currentIngredients?.add(
            Ingredient(
                name = ingredientName,
                originalName = ingredientName,
                customMeasure = ingredientMesaure
            )
        )

        currentRecipe?.apply {
            this.ingredients = currentIngredients
        }
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun deleteIngredient(ingredient: Ingredient) {
        val currentRecipe = newRecipe.value?.data
        val currentIngredients = currentRecipe?.getAllIngredients()?.toMutableList()

        currentIngredients?.remove(ingredient)

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

    override fun addDiets(diets: List<String?>) {
        val currentRecipe = newRecipe.value?.data
        currentRecipe?.diets = diets
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun addIntolerances(intolerances: List<String?>) {
        val currentRecipe = newRecipe.value?.data
        currentRecipe?.intolerances = intolerances
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun addNutrition(calories: Int, fat: Int, protein: Int, carbs: Int) {
        val currentRecipe = newRecipe.value?.data
        val nutritionWrapper = newRecipe.value?.data?.nutrition ?: NutritionWrapper()
        val listOfNutrient = mutableListOf<Nutrient>()

        //  Calories
        listOfNutrient.add(
            Nutrient(
                name = KEY_CALORIES,
                amount = calories.toFloat(),
                unit = KEY_CALORIES_UNIT,
                percentOfDailyNeeds = getCaloriesPercentOfDailyNeeds(calories)
            )
        )

        //  Fats
        listOfNutrient.add(
            Nutrient(
                name = KEY_FAT,
                amount = fat.toFloat(),
                unit = KEY_FAT_UNIT,
                percentOfDailyNeeds = getFatsPercentOfDailyNeeds(fat)
            )
        )

        //  Proteins
        listOfNutrient.add(
            Nutrient(
                name = KEY_PROTEIN,
                amount = protein.toFloat(),
                unit = KEY_PROTEIN_UNIT,
                percentOfDailyNeeds = getProteinsPercentOfDailyNeeds(protein)
            )
        )

        //  Carbs
        listOfNutrient.add(
            Nutrient(
                name = KEY_CARBOHYDRATES,
                amount = carbs.toFloat(),
                unit = KEY_CARBOHYDRATES_UNIT,
                percentOfDailyNeeds = getCarbohydratesPercentOfDailyNeeds(carbs)
            )
        )

        Timber.d("addNutrition: ${listOfNutrient.toString()}")

        nutritionWrapper.nutrients = listOfNutrient
        currentRecipe?.nutrition = nutritionWrapper
        newRecipe.postValue(Resource.success(currentRecipe))
    }

    override fun saveNewRecipe() {
        val currentRecipe = newRecipe.value?.data
        currentRecipe?.uid = UUID.randomUUID().toString()
        currentRecipe?.let {
            db.customRecipesDao().saveRecipe(it)
        }
        newRecipe.postValue(Resource.success(currentRecipe))
        userRepository.updateCustomRecipes()
    }
}