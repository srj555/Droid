package com.sr.myapplication.core.app

import android.app.Application
import com.sr.myapplication.core.di.AppComponent
import com.sr.myapplication.core.di.AppModule
import com.sr.myapplication.core.di.DaggerAppComponent

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = buildAppComponent()
    }

    companion object {
        var appComponent: AppComponent? = null
            private set

        fun buildAppComponent(): AppComponent {
            return DaggerAppComponent.builder().appModule(AppModule()).build()
        }
    }
}