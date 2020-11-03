package com.lonchi.andrej.lonchi_bakalarka.ui.camera

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.data.repository.ImageLabelingRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingResponse
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CameraViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val imageLabelingRepository: ImageLabelingRepository
) : BaseViewModel() {

    val imageLabelingState: MutableLiveData<Resource<ImageLabelingResponse>> =
        MutableLiveData<Resource<ImageLabelingResponse>>().apply { postValue(Resource.notStarted()) }

    fun selectedIngredients(selectedIngredients: List<Ingredient>) {
        Timber.d("selectedIngredients: ${selectedIngredients.size}")
        selectedIngredients.forEach {
            Timber.d("selectedIngredients: $it")
        }
    }

    fun imageLabelingProcess(imageUri: Uri?) {
        compositeDisposable.add(
            imageLabelingRepository.firebaseImageLabelingOnCloud(imageUri)
                .doOnSubscribe {
                    imageLabelingState.postValue(Resource.loading())
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("imageLabelingProcess success: $it")
                    imageLabelingState.postValue(it)
                }, {
                    Timber.e("imageLabelingProcess error: $it")
                    imageLabelingState.postValue(Resource.error(ErrorIdentification.Unknown(it.message)))
                })
        )
    }
}
