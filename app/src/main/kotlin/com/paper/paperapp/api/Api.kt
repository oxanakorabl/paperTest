package com.paper.paperapp.api

import com.paper.paperapp.api.objects.BaseResponse
import com.paper.paperapp.api.objects.Product
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface Api {

    @POST("pushes/apps")
    fun sendPushToken(@Body newToken: Product): Single<BaseResponse<Any>>


    @Headers("Content-Type: application/json")
    @GET("geo/address")
    fun getAddress(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Header("id") cityId: String = ""
    ): Single<BaseResponse<Product>>


    @HTTP(method = "DELETE", path = "/client/{clientId}/card", hasBody = true)
    @Headers("Content-Type: application/json")
    fun deleteCard(
        @Path("clientId") clientId: String,
        @Body request: Product
    ): Single<BaseResponse<Any>>


    //http://qaru.site/questions/456372/upload-picture-to-server-using-retrofit-2
    @POST("/review/image")
    fun uploadPhoto(
        @Header("Content-Type") contentType: String,
        @Body body: MultipartBody
    ): Single<BaseResponse<Any>>

}

