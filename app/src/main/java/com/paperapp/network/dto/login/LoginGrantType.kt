package com.paperapp.network.dto.login

enum class LoginGrantType(val type: String) {
    GUEST("guest_auth"),
    VK("vk_auth"),
    FB("facebook_auth"),
    GOOGLE("google_auth"),
    REFRESH("refresh_token")
}