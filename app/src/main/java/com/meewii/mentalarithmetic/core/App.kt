package com.meewii.mentalarithmetic.core

import com.meewii.mentalarithmetic.dagger.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().create(this).apply { inject(this@App) }

}