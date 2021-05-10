package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.repository.DiscoverByIngredientsRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsListViewModel @Inject constructor(
    private val rootRepository: DiscoverByIngredientsRepository,
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

    val ingredients: MutableLiveData<List<String>> = rootRepository.ingredients

    fun removeIngredient(value: String) = rootRepository.removeIngredient(value)

    fun getIngredients(): Array<String> = ingredients.value?.toTypedArray() ?: arrayOf()
}