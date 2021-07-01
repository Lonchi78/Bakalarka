package com.lonchi.andrej.lonchi_bakalarka.data.utils

import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.BaseResponse
import com.squareup.moshi.Moshi
import retrofit2.Response


/**
 * A generic class that contains data and status description loading this data.
 *
 * @author Google
 */
class Resource<out T> constructor(
    val status: Status,
    val data: T?,
    val errorIdentification: ErrorIdentification = ErrorIdentification.None()
) {

    companion object {

        fun <T> notStarted(data: T? = null): Resource<T> =
                Resource(
                        NotStartedStatus(),
                        data
                )

        fun <T> success(data: T?): Resource<T> =
                Resource(
                        SuccessStatus(),
                        data
                )

        fun <T> error(errorIdentification: ErrorIdentification, data: T? = null): Resource<T> =
                Resource(
                        ErrorStatus(),
                        data,
                        errorIdentification
                )

        fun <T> loading(data: T? = null): Resource<T> =
                Resource(
                        LoadingStatus(),
                        data
                )

        fun <T> voidDataError(origin: Resource<*>): Resource<T> =
                error(origin.errorIdentification, null)

        fun <T> parseResult(moshi: Moshi, item: Response<T>): Resource<T> {
            val errorBodyJson = item.errorBody()?.string()
            val errorMessage = if (errorBodyJson != null) {
                moshi.adapter(BaseResponse::class.java).fromJson(errorBodyJson).message
            } else {
                null
            }

            return when (item.code()) {
                BaseResponse.CODE_SUCCESS -> success(item.body())
                BaseResponse.CODE_UNAUTHORIZED -> error(ErrorIdentification.Authentication(errorMessage), item.body())
                BaseResponse.CODE_NOT_FOUND -> error(ErrorIdentification.NotFound(errorMessage), item.body())
                BaseResponse.CODE_VALIDATION_ERROR -> error(ErrorIdentification.Validation(errorMessage), item.body())
                BaseResponse.CODE_METHOD_NOT_ALLOWED -> error(ErrorIdentification.MethodNotAllowed(errorMessage), item.body())
                BaseResponse.CODE_INTERNAL_SERVER_ERROR -> error(ErrorIdentification.InternalServerError(errorMessage), item.body())
                BaseResponse.CODE_NOT_IMPLEMENTED -> error(ErrorIdentification.NotImplemented(errorMessage), item.body())
                BaseResponse.CODE_BAD_GATEWAY -> error(ErrorIdentification.BadGateway(errorMessage), item.body())
                else -> error(ErrorIdentification.Unknown(), item.body())
            }
        }
    }

    fun <B> mapData(mapper: (T) -> B): Resource<B> = Resource(
            status = this.status,
            errorIdentification = this.errorIdentification,
            data = data?.let { mapper.invoke(it) })
}

sealed class Status(private val id: Int)
class NotStartedStatus : Status(0)
class SuccessStatus : Status(1)
class ErrorStatus : Status(2)
class LoadingStatus : Status(3)

sealed class ErrorIdentification(val message: String, val serverCode: Int? = null) {
    class Unknown(message: String? = null) : ErrorIdentification(message ?: "Unknown error")
    open class Authentication(message: String? = null) : ErrorIdentification("User authentication failed")
    class Validation(message: String?) : ErrorIdentification(message ?: "Validation error")
    class NotFound(message: String?) : ErrorIdentification(message ?: "404 Not Found")
    class None : ErrorIdentification("")
    class Connection : ErrorIdentification("Connection failed")
    class DataError : ErrorIdentification("Data error")
    class Timeout : ErrorIdentification("Timeout")
    class InternalServerError(message: String?) : ErrorIdentification(message ?: "Internal server error")
    class BadEmailOrPassword(message: String?) : ErrorIdentification(message ?: "Bad email or password")
    class BadGateway(message: String?) : ErrorIdentification(message ?: "Bad gateway")
    class NotImplemented(message: String?) : ErrorIdentification(message ?: "Not implemented")
    class MethodNotAllowed(message: String?) : ErrorIdentification(message ?: "Method not allowed")
    class ErrorWithMessage(message: String, code: Int) : ErrorIdentification(message, code)

    class BitmapIsNull(message: String? = null) : ErrorIdentification(message ?: "Cant't get bitmap of image")

    fun parseLog() = "${this.javaClass.simpleName}: $message"
}


