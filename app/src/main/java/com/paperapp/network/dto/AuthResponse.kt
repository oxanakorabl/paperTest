package com.paperapp.network.dto

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("access_token") val access_token: String,
    @SerializedName("token_type") val token_type: String,
    @SerializedName("expires_in") val expires_in: Int,
    @SerializedName("refresh_token") val refresh_token: String,
    @SerializedName(".issued") val issued: String,
    @SerializedName(".expires") val expires: String,
    @SerializedName("user_name") val user_name: String,
    @SerializedName("user_id") val user_id: String,
    @SerializedName("client_id") val client_id: String,
    @SerializedName("isFirstFbLogin") val isFirstFbLogin: Boolean,
    @SerializedName("isFirstVkLogin") val isFirstVkLogin: Boolean,
    @SerializedName("isGuest") val isGuest: Boolean,
    @SerializedName("hasFbAccount") val hasFbAccount: Boolean,
    @SerializedName("hasVkAccount") val hasVkAccount: Boolean,
    @SerializedName("hasGAAccount") val hasGAAccount: Boolean,
    @SerializedName("hasEmail") val hasEmail: Boolean,
    @SerializedName("currentLoginProvider") val currentLoginProvider: String

)