package com.paper.paperapp.dagger

import com.paper.paperapp.dagger.modules.AppModule
import com.paper.paperapp.dagger.modules.NetworkModule

object ComponentHolder {

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .appModule(AppModule())
        .networkModule(NetworkModule())
        .build()

}
