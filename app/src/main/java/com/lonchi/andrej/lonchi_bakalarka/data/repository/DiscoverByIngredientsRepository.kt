package com.lonchi.andrej.lonchi_bakalarka.data.repository

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface DiscoverByIngredientsRepository {

    val ingredients: MutableLiveData<List<String>>

    fun resetIngredients()

    fun addIngredient(value: String)
    fun addIngredients(value: List<String>)

    fun removeIngredient(value: String)
}

class DiscoverByIngredientsRepositoryImpl @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    prefs: SharedPreferencesInterface,
    retrofit: Retrofit
) : BaseRepository(db, api, prefs, retrofit), DiscoverByIngredientsRepository {

    override val ingredients: MutableLiveData<List<String>> = MutableLiveData<List<String>>().apply {
        postValue(listOf())
    }

    override fun resetIngredients() {
        ingredients.postValue(listOf())
    }

    override fun addIngredient(value: String) {
        (ingredients.value?.toMutableList() ?: mutableListOf()).apply {
            Timber.d("addIngredient: $this`")
            this.add(value)
            Timber.d("addIngredient: $this")
            ingredients.postValue(this)
        }
    }

    override fun addIngredients(value: List<String>) {
        (ingredients.value?.toMutableList() ?: mutableListOf()).apply {
            Timber.d("addIngredient: $this`")
            this.addAll(value)
            Timber.d("addIngredient: $this")
            ingredients.postValue(this)
        }
    }

    override fun removeIngredient(value: String) {
        (ingredients.value?.toMutableList() ?: mutableListOf()).apply {
            this.remove(value)
            ingredients.postValue(this)
        }
    }
}