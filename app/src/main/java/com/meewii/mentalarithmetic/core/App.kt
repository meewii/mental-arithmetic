package com.meewii.mentalarithmetic.core

import android.app.Application
import com.meewii.mentalarithmetic.dagger.components.AppComponent
import com.meewii.mentalarithmetic.dagger.components.DaggerAppComponent
import com.meewii.mentalarithmetic.dagger.modules.AppModule


class App : Application() {

    private val appComponent: AppComponent by lazy { DaggerAppComponent.builder().appModule(AppModule(this@App)).build() }

    fun appComponent(): AppComponent? = appComponent

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}