package com.paper.paperapp.dagger.modules

import android.content.Context
import com.paper.paperapp.PaperApp
import com.paper.paperapp.util.NetworkHelper
import com.paper.paperapp.util.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = PaperApp.INSTANCE

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    @Provides
    @Singleton
    fun provideResourceProvider(context: Context) = ResourceProvider(context)

}
