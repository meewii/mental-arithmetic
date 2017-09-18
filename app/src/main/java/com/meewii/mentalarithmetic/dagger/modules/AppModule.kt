package com.meewii.mentalarithmetic.dagger.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.meewii.mentalarithmetic.dagger.qualifiers.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    @ForApplication
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideSharedPreferences(@ForApplication context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

}