package com.meewii.mentalarithmetic.dagger.modules

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.meewii.mentalarithmetic.core.App
import com.meewii.mentalarithmetic.dagger.components.ViewModelComponent
import com.meewii.mentalarithmetic.dagger.scopes.ActivityScope
import com.meewii.mentalarithmetic.ui.game.GameActivity
import com.meewii.mentalarithmetic.ui.game.ViewModelFactory
import com.meewii.mentalarithmetic.ui.nav.HomeNavActivity
import com.meewii.mentalarithmetic.ui.nav.PickDifficultyNavActivity
import com.meewii.mentalarithmetic.ui.nav.PickOperatorNavActivity
import com.meewii.mentalarithmetic.ui.score.ScoreActivity
import com.meewii.mentalarithmetic.ui.score.ScoreFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Suppress("unused")
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

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf())
    internal abstract fun contributeHomeActivityInjector(): HomeNavActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf())
    internal abstract fun contributePickOperatorNavActivityInjector(): PickOperatorNavActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf())
    internal abstract fun contributePickDifficultyNavActivityInjector(): PickDifficultyNavActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf())
    internal abstract fun contributeScoreActivityInjector(): ScoreActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf()) /* modules to install into the subcomponent */
    internal abstract fun contributeGameActivityInjector(): GameActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf())
    internal abstract fun contributeScoreFragmentInjector(): ScoreFragment

}