package com.paperapp.network.dto.login

import com.google.gson.annotations.SerializedName

data class LoginViaEmailConfirmationBody(
    @SerializedName("client_id") val clientId: String,
    @SerializedName("client_secret") val clientSecret: String,
    @SerializedName("callback_parameter") val callbackParameter: String
)