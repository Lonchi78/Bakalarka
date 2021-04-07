package com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.repository.DiscoverByIngredientsRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class IngredientsListViewModel @Inject constructor(
    private val rootRepository: DiscoverByIngredientsRepository
) : BaseViewModel() {

    val ingredients: MutableLiveData<List<String>> = rootRepository.ingredients

    fun resetIngredients() = rootRepository.resetIngredients()

    fun removeIngredient(value: String) = rootRepository.removeIngredient(value)

}