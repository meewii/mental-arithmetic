package com.meewii.mentalarithmetic.core

import android.app.Application
import com.meewii.mentalarithmetic.dagger.components.AppComponent
import com.meewii.mentalarithmetic.dagger.components.DaggerAppComponent
import com.meewii.mentalarithmetic.dagger.modules.ActivityModule
import com.meewii.mentalarithmetic.dagger.modules.AppModule
import com.meewii.mentalarithmetic.ui.activities.BaseActivity


class App : Application() {

    private val appComponent: AppComponent by lazy { DaggerAppComponent.builder().appModule(AppModule(this@App)).build() }

    fun applicationComponent(): AppComponent? = appComponent
    fun activityComponent(activity: BaseActivity) = appComponent.plusActivity(ActivityModule(activity))

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}