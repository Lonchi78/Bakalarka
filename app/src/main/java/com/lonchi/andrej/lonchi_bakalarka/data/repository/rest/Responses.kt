package com.lonchi.andrej.lonchi_bakalarka.data.repository.rest

import com.lonchi.andrej.lonchi_bakalarka.data.entities.User
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
abstract class BaseResponse(
    @Json(name = "StatusCode") val status: Int,
    @Json(name = "Message") val error: String?
) {
    companion object {
        const val CODE_SUCCESS = 200
        const val CODE_SUCCESS_CREATE = 201
        const val CODE_WRONG_ACCESS_TOKEN = 300
        const val CODE_LOGIN_ERROR = 301
        const val CODE_VALIDATION_ERROR = 400
        const val CODE_UNAUTHORIZED = 401
        const val CODE_BAD_EMAIL_OR_PASSWORD = 403
        const val CODE_NOT_FOUND = 404
        const val CODE_METHOD_NOT_ALLOWED = 405
        const val CODE_PHP_ERROR_500 = 500
        const val CODE_PHP_ERROR_501 = 501
        const val CODE_PHP_ERROR_502 = 502
    }

    open fun isSuccessful(): Boolean = status == CODE_SUCCESS || status == CODE_SUCCESS_CREATE

    fun mapToError(): ErrorIdentification = when {
        isSuccessful() -> ErrorIdentification.None()
        status == CODE_UNAUTHORIZED -> ErrorIdentification.Authentication()
        status == CODE_VALIDATION_ERROR -> ErrorIdentification.Validation(error)
        status == CODE_BAD_EMAIL_OR_PASSWORD -> ErrorIdentification.BadEmailOrPassword(error)
        status == CODE_NOT_FOUND -> ErrorIdentification.NotFound(error)
        status == CODE_PHP_ERROR_500 -> ErrorIdentification.ServerError(status, error.orEmpty())
        status == CODE_BAD_EMAIL_OR_PASSWORD -> ErrorIdentification.BadEmailOrPassword(error)
        error.orEmpty().isNotEmpty() -> ErrorIdentification.ErrorWithMessage(error.orEmpty())
        else -> ErrorIdentification.Unknown()
    }

}

class ErrorResponse(status: Int, error: String?) : BaseResponse(status, error) {
    override fun isSuccessful(): Boolean = false
}

class ItemsResponse<T>(
    status: Int,
    error: String?,
    @Json(name = "data")
    val items: List<T>
) : BaseResponse(status, error)

class DataResponse<T>(
    StatusCode: Int,
    Message: String?,
    @Json(name = "Data") val data: T
) : BaseResponse(StatusCode, Message)

data class UserWrapper(val user: User)