package com.paperapp.configuration

import com.paperapp.BuildConfig

enum class ConfigurationOfBackend(
    val id: String,
    val apiUrl: String,
    val apiKey: String, /* ToDo: rename: clientSecret */
    val clientId: String
) {
    DEV(
        id = "dev",
        apiUrl = "https://dev.paperapp.ru/",
        apiKey = "0000000000",
        clientId = "androidApp"
    ),

    PROD(
        id = "prod",
        apiUrl = "https://paperapp.ru/",
        apiKey = "todo", // ToDo
        clientId = "androidApp"
    );

    companion object {
        fun getCurrent(): ConfigurationOfBackend {
            return values().find { it.id == BuildConfig.FLAVOR } ?: DEV

        }
    }
}