package com.paperapp.dagger.modules

import android.content.Context
import com.paperapp.PaperApplication
import com.paperapp.util.NetworkHelper
import com.paperapp.util.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context = PaperApplication.INSTANCE

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    @Provides
    @Singleton
    fun provideResourceProvider(context: Context) = ResourceProvider(context)
}