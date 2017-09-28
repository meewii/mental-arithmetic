package com.meewii.mentalarithmetic.dagger.modules

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.meewii.mentalarithmetic.core.App
import com.meewii.mentalarithmetic.dagger.components.ViewModelComponent
import com.meewii.mentalarithmetic.dagger.scopes.ActivityScope
import com.meewii.mentalarithmetic.ui.game.GameActivity
import com.meewii.mentalarithmetic.ui.game.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Module(includes = arrayOf(AndroidInjectionModule::class),
        subcomponents = arrayOf(ViewModelComponent::class))
abstract class AppModule {

    @Module
    companion object {
        @Singleton
        @Provides
        @JvmStatic
        fun provideViewModelFactory(viewModelComponent: ViewModelComponent.Builder): ViewModelProvider.Factory = ViewModelFactory(viewModelComponent.build())
    }

    @Binds
    @Singleton
    abstract fun application(app: App): Application
//
//    @ForApplication
//    fun provideContext(app: App): Context = app
//
//
//    @ActivityScope
//    @ContributesAndroidInjector()
//    internal abstract fun gameActivity(): GameActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf())/* modules to install into the subcomponent */
    internal abstract fun contributeGameActivityInjector(): GameActivity


}