package com.paperapp.dagger.modules

import android.content.Context
import com.paperapp.storage.PushTokenStorage
import com.paperapp.storage.UserInfoStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideUserInfoStorage(context: Context) = UserInfoStorage(context)


    @Provides
    @Singleton
    fun providePushStorage(context: Context) = PushTokenStorage(context)

}