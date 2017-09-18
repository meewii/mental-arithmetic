package com.meewii.mentalarithmetic.dagger.components

import com.meewii.mentalarithmetic.core.App
import com.meewii.mentalarithmetic.dagger.modules.ActivityModule
import com.meewii.mentalarithmetic.dagger.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(application: App)
    fun plusActivity(activityModule: ActivityModule): ActivityComponent
}