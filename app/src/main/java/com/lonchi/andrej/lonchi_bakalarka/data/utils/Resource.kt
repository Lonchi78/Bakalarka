package com.lonchi.andrej.lonchi_bakalarka.data.utils


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

        fun <T> error(errorIdentification: ErrorIdentification, data: T?): Resource<T> =
                Resource(
                        ErrorStatus(),
                        data,
                        errorIdentification
                )

        fun <T> loading(data: T?): Resource<T> =
                Resource(
                        LoadingStatus(),
                        data
                )

        fun <T> voidDataError(origin: Resource<*>): Resource<T> =
                error(origin.errorIdentification, null)

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
    open class Authentication : ErrorIdentification("User authentication failed")
    class Validation(message: String?) : ErrorIdentification(message ?: "Validation error")
    class NotFound(message: String?) : ErrorIdentification(message ?: "404 Not Found")
    class None : ErrorIdentification("")
    class Connection : ErrorIdentification("Connection failed")
    class DataError : ErrorIdentification("Data error")
    class Timeout : ErrorIdentification("Timeout")
    class ServerError(code: Int?, message: String) : ErrorIdentification(message, code)
    class ErrorWithMessage(message: String) : ErrorIdentification(message)
    class AuthenticationExpired : Authentication()
    class UserDisabled : Authentication()
    class UserNotFoundOnDevice : Authentication()
    class BadEmailOrPassword(message: String?) : ErrorIdentification(message ?: "Incorrect email or password")

    fun parseLog() = "${this.javaClass.simpleName}: $message"
}


