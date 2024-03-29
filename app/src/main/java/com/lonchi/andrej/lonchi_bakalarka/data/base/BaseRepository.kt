package com.lonchi.andrej.lonchi_bakalarka.data.base

import com.lonchi.andrej.lonchi_bakalarka.data.repository.database.LonchiDatabase
import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.BaseResponse
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ErrorResponse
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.RestApi
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
abstract class BaseRepository(
    protected val db: LonchiDatabase,
    protected val api: RestApi,
    protected val basicSharedPreferences: SharedPreferencesInterface,
    protected val retrofit: Retrofit
) {
    companion object {
        const val SYNC_TIMEOUT_SECONDS = 15L
        const val SYNC_RETRY_ATTEMPTS = 3L
    }

    protected val TIMBER_TAG = "${this.javaClass.simpleName}: %s"
    protected val moshi by lazy { Moshi.Builder().build() }

    /**
     * Extension handling server calls where return types do matter
     * @param retries amount of retries before error is returned
     */
    protected fun <U, T : Response<U>> Single<T>.asSyncOperation(retries: Long = SYNC_RETRY_ATTEMPTS): Single<Resource<U>> =
            this.timeout(SYNC_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .retry(retries)
                    .map { Resource.parseResult(moshi, it) }
                    .onErrorReturn { parseErrorReturn(it) }
                    .subscribeOn(Schedulers.io())
                    .doOnDispose { Timber.d(TIMBER_TAG, "Call disposed") }

    /**
     * Attempts automatic parse of failed server response
     * @param response
     */
    protected fun <T> parseFailedResponse(response: Response<T>): Resource<T> =
            try {
                val error = moshi.adapter(ErrorResponse::class.java).fromJson(response.errorBody()?.string())
                Resource.error(error.mapToError(), null)
            } catch (e: Exception) {
                try {
                    parseErrorBody<T>(response.errorBody()!!)
                } catch (e: Exception) {
                    Resource.error(ErrorIdentification.Unknown(), null)
                }

            }

    /**
     * Attempts automatic parse of failed request call
     * @param throwable
     */
    private fun <T> parseErrorReturn(throwable: Throwable): Resource<T> {
        Timber.i(throwable)
        when (throwable) {
            is UnknownHostException -> {
                return Resource.error(ErrorIdentification.Connection(), null)
            }
            is HttpException -> {
                if (throwable.code() == 401) {
                    return Resource.error(ErrorIdentification.Authentication(), null)
                }

                val errorBody = throwable.response()?.errorBody()
                if (errorBody != null) {
                    return parseErrorBody(errorBody)
                }
            }
            is TimeoutException -> return Resource.error(ErrorIdentification.Timeout(), null)
            is JsonDataException -> return Resource.error(ErrorIdentification.DataError(), null)
        }
        return Resource.error(ErrorIdentification.Unknown(), null)
    }

    private fun <T> parseErrorBody(errorBody: ResponseBody): Resource<T> {
        val response = retrofit.responseBodyConverter<Any>(
                ErrorResponse::class.java, ErrorResponse::class.java.getAnnotations()
        )
                .convert(errorBody) as ErrorResponse
        return Resource.error(response.mapToError(), null)
    }

    /**
     * Adds action to flow of request that failed on authorization
     * @param action action to invoke
     */
    protected fun <T> Single<Resource<T>>.performUnauthorizedAction(action: (T?) -> Unit) =
            this.flatMap {
                return@flatMap if (it.status is ErrorStatus && it.errorIdentification is ErrorIdentification.Authentication)
                    Single.fromCallable {
                        action(it.data)
                        return@fromCallable it
                    }
                else Single.just(it)
            }
}