package com.paperapp.network.dto.login

import com.google.gson.annotations.SerializedName

data class LoginViaEmailBody(
    @SerializedName("EmailAddress") val emailAddress: String
)