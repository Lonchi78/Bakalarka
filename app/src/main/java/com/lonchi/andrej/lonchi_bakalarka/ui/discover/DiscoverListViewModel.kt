package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverListViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

    fun searchRecipesByQuery(query: String) {
        Timber.d("serachRecipesByQuery: $query")
    }

}