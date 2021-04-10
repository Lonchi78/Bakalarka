package com.lonchi.andrej.lonchi_bakalarka.data.repository.rest

import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.User
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.squareup.moshi.Json


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
abstract class BaseResponse(
    @Json(name = "code") val code: Int = 0,
    @Json(name = "status") val status: String? = null,
    @Json(name = "message") val message: String? = null
) {
    companion object {
        const val CODE_SUCCESS = 200
        const val CODE_SUCCESS_CREATE = 201
        const val CODE_VALIDATION_ERROR = 400
        const val CODE_UNAUTHORIZED = 401
        const val CODE_NOT_FOUND = 404
        const val CODE_METHOD_NOT_ALLOWED = 405
        const val CODE_INTERNAL_SERVER_ERROR = 500
        const val CODE_NOT_IMPLEMENTED = 501
        const val CODE_BAD_GATEWAY = 502
    }

    open fun isSuccessful(): Boolean = code == CODE_SUCCESS || code == CODE_SUCCESS_CREATE

    fun mapToError(): ErrorIdentification = when {
        isSuccessful() -> ErrorIdentification.None()
        code == CODE_UNAUTHORIZED -> ErrorIdentification.Authentication(message)
        code == CODE_VALIDATION_ERROR -> ErrorIdentification.Validation(message)
        code == CODE_NOT_FOUND -> ErrorIdentification.NotFound(message)
        code == CODE_NOT_FOUND -> ErrorIdentification.NotFound(message)
        code == CODE_VALIDATION_ERROR -> ErrorIdentification.Validation(message)
        code == CODE_METHOD_NOT_ALLOWED -> ErrorIdentification.MethodNotAllowed(message)
        code == CODE_INTERNAL_SERVER_ERROR -> ErrorIdentification.InternalServerError(message)
        code == CODE_NOT_IMPLEMENTED -> ErrorIdentification.NotImplemented(message)
        code == CODE_BAD_GATEWAY -> ErrorIdentification.BadGateway(message)
        message.orEmpty().isNotEmpty() -> ErrorIdentification.ErrorWithMessage(message.orEmpty(), code)
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

data class ImageLabelingItem(
    val item: String,
    val entityId: String?,
    val confidence: Float
)
data class ImageLabelingResponse(val items: List<ImageLabelingItem>)

data class RecipesResponse(
    @Json(name = "recipes") val recipes: List<Recipe>
): BaseResponse()

data class SearchRecipesResponse(
    @Json(name = "results") val results: List<Recipe>,
    @Json(name = "baseUri") val baseUri: String,
    @Json(name = "isStale") val isStale: Boolean,
    @Json(name = "offset") val offset: Int,
    @Json(name = "number") val number: Int,
    @Json(name = "totalResults") val totalResults: Int,
    @Json(name = "processingTimeMs") val processingTimeMs: Int,
    @Json(name = "expires") val expires: Long
): BaseResponse()

data class SearchRecipesByIngredientsResponse(
    @Json(name = "results") val results: List<Recipe>
): BaseResponse()