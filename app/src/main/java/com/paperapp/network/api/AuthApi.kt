package com.paperapp.network.api

import com.paperapp.configuration.ConfigurationOfBackend
import com.paperapp.network.dto.AuthResponse
import com.paperapp.network.dto.BaseResponse
import com.paperapp.network.dto.BaseResponseEmptyBody
import com.paperapp.network.dto.login.LoginGrantType
import com.paperapp.network.dto.login.LoginViaEmailBody
import io.reactivex.Single
import retrofit2.http.*

interface AuthApi {
    @FormUrlEncoded
    @POST("token")
    fun loginAsGuest(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = LoginGrantType.GUEST.type
    ): Single<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("api/account/LoginCallback")
    fun loginViaEmailConfirmation(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("callback_parameter") callbackParameter: String
    ): Single<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("token")
    fun refreshToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = LoginGrantType.REFRESH.type,
        @Field("refresh_token") refreshToken: String
    ): Single<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("token")
    fun loginVK(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = LoginGrantType.VK.type,
        @Field("email") email: String,
        @Field("token") token: String,
        @Field("user_id") userId: String,
        @Field("link_confirmed") link_confirmed: Boolean
    ): Single<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("token")
    fun loginGoogle(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = LoginGrantType.GOOGLE.type,
        @Field("token") token: String
    ): Single<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("token")
    fun loginFB(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = LoginGrantType.FB.type,
        @Field("token") token: String,
        @Field("link_confirmed") link_confirmed: Boolean
    ): Single<BaseResponse<AuthResponse>>

    @POST("api/account/login")
    fun requestLoginEmail(
        @Body body: LoginViaEmailBody
    ): Single<BaseResponse<BaseResponseEmptyBody>>
}