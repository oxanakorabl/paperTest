package com.paperapp.dagger

import android.content.Context
import com.google.gson.Gson
import com.paperapp.dagger.modules.*
import com.paperapp.network.api.AuthApi
import com.paperapp.storage.PushTokenStorage
import com.paperapp.storage.UserInfoStorage
import com.paperapp.util.NetworkHelper
import com.paperapp.util.ResourceProvider
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AppModule::class, NetworkModule::class, StorageModule::class, RepositoryModule::class]
)
interface AppComponent {
    fun createLoginComponent(loginModule: LoginModule): LoginComponent

    fun getContext(): Context
    fun getGson(): Gson

    fun getUserInfoStorage(): UserInfoStorage
    fun getPushTokenStorage(): PushTokenStorage

    fun getNetworkHelper(): NetworkHelper
    fun getResourceProvider(): ResourceProvider

    fun getAuthApi(): AuthApi


//    fun getSearchInteractor(): SearchInteractor
//    fun getBitmapProvider(): BitmapProvider
//    fun getLocationProvider(): LocationProvider
//    fun getPlacesProvider(): PlacesProvider

}
