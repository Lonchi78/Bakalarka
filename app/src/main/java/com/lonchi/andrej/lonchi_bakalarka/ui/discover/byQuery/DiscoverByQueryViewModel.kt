package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byQuery

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.SearchRecipesResponse
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByQueryViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : BaseViewModel() {

    val searchRecipeState: MutableLiveData<Resource<SearchRecipesResponse>> = MutableLiveData<Resource<SearchRecipesResponse>>().apply {
        postValue(Resource.notStarted())
    }

    fun searchRecipesByQuery(query: String) {
        Timber.d("serachRecipesByQuery: $query")
        compositeDisposable.add(
            recipesRepository.searchRecipesByQuery(query)
                .subscribe({ response ->
                    Timber.d("searchRecipesByQuery status: ${response.status}")
                    Timber.d("searchRecipesByQuery errId: ${response.errorIdentification}")
                    Timber.d("searchRecipesByQuery recipes size: ${response.data?.results?.size}")
                    searchRecipeState.postValue(response)
                }, {
                    Timber.e("searchRecipesByQuery: $it")
                    searchRecipeState.postValue(Resource.error(ErrorIdentification.Unknown()))
                })
        )
    }

}