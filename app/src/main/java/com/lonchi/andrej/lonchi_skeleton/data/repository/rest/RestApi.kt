package com.lonchi.andrej.lonchi_skeleton.data.repository.rest

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
interface RestApi {
    companion object {
        private const val HEADER_AUTHORIZATION = "access-token"
    }

    @POST("user/register")
    fun register(@Body data: TestRequestData): Single<Response<DataResponse<UserWrapper>>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("email") email: String,
              @Field("password") password: String,
              @Field("device") device: String): Single<Response<DataResponse<UserWrapper>>>

    @FormUrlEncoded
    @POST("user/logout")
    fun logout(@Header(HEADER_AUTHORIZATION) accessToken: String): Single<Response<ItemsResponse<Any>>>

    @POST("user/update-email")
    fun updateEmail(@Header(HEADER_AUTHORIZATION) accessToken: String,
                    @Body data: TestRequestData
    ): Single<Response<DataResponse<UserWrapper>>>

    @POST("user/update")
    fun userUpdate(@Header(HEADER_AUTHORIZATION) accessToken: String,
                   @Body data: TestRequestData
    ): Single<Response<DataResponse<UserWrapper>>>

    @POST("user/change-password")
    fun changePassword(@Header(HEADER_AUTHORIZATION) accessToken: String,
                       @Body data: TestRequestData
    ): Single<Response<ItemsResponse<Any>>>

    @POST("user/request-password-reset")
    fun resetPassword(@Header(HEADER_AUTHORIZATION) accessToken: String,
                      @Body data: TestRequestData
    ): Single<ItemsResponse<Any>>

    @GET("user/{id}")
    fun syncUser(@Header(HEADER_AUTHORIZATION) accessToken: String,
                 @Path(value = "id", encoded = true) userId: String): Single<Response<DataResponse<UserWrapper>>>

}