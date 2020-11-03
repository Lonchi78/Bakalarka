package com.lonchi.andrej.lonchi_bakalarka.data.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions
import com.lonchi.andrej.lonchi_bakalarka.data.base.BaseRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingResponse
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.toImageLabelingResponse
import com.lonchi.andrej.lonchi_bakalarka.data.utils.DeviceTracker
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.logic.util.uriToBitmap
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface ImageLabelingRepository {

    companion object {
        const val DEFAULT_CONFIDENCE_THRESHOLD = 0.7f
    }

    fun firebaseImageLabelingOnCloud(
        imageUri: Uri?,
        confidenceThreshold: Float = DEFAULT_CONFIDENCE_THRESHOLD
    ): Single<Resource<ImageLabelingResponse>>
}

class ImageLabelingRepositoryImpl @Inject internal constructor(
    api: RestApi,
    db: LonchiDatabase,
    private val prefs: SharedPreferencesInterface,
    retrofit: Retrofit,
    private val deviceTracker: DeviceTracker,
    private val appContext: Context,
) : BaseRepository(db, api, prefs, retrofit), ImageLabelingRepository {

    override fun firebaseImageLabelingOnCloud(
        imageUri: Uri?,
        confidenceThreshold: Float
    ): Single<Resource<ImageLabelingResponse>> {
        val bitmap = appContext.uriToBitmap(imageUri)
            ?: return Single.fromCallable { Resource.error(ErrorIdentification.BitmapIsNull()) }

        return Single.create { emitter ->
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            val options = FirebaseVisionCloudImageLabelerOptions.Builder()
                .setConfidenceThreshold(confidenceThreshold)
                .build()
            val labeler = FirebaseVision.getInstance().getCloudImageLabeler(options)

            labeler.processImage(image)
                .addOnSuccessListener {
                    Timber.d("detectDeliciousFoodOnCloud: result = ${it.size}")
                    it.forEach {
                        Timber.d("detectDeliciousFoodOnCloud item: ${it.text} | ${it.entityId} | ${it.confidence}")
                    }
                    //  TODO - maybe take just first N items
                    emitter.onSuccess(Resource.success(it.toImageLabelingResponse()))
                }
                .addOnFailureListener {
                    Timber.e("detectDeliciousFoodOnCloud: error $it")
                    emitter.onError(it)
                }
        }
    }



}