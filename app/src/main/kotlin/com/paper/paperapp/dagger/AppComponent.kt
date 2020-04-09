package com.paper.paperapp.dagger

import android.content.Context
import com.google.gson.Gson
import com.paper.paperapp.api.Api
import com.paper.paperapp.dagger.modules.AppModule
import com.paper.paperapp.dagger.modules.NetworkModule
import com.paper.paperapp.dagger.modules.StorageModule
import com.paper.paperapp.storage.PushTokenStorage
import com.paper.paperapp.storage.UserInfoStorage
import com.paper.paperapp.util.NetworkHelper
import com.paper.paperapp.util.ResourceProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, StorageModule::class))
interface AppComponent {

    fun getContext(): Context
    fun getGson(): Gson
    fun getUserInfoStorage(): UserInfoStorage
    fun getPushTokenStorage(): PushTokenStorage
    fun getApi(): Api
    fun getNetworkHelper(): NetworkHelper
    fun getResourceProvider(): ResourceProvider


//    fun getSearchInteractor(): SearchInteractor
//    fun getBitmapProvider(): BitmapProvider
//    fun getLocationProvider(): LocationProvider
//    fun getPlacesProvider(): PlacesProvider

}
