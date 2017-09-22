package com.meewii.mentalarithmetic.dagger.modules

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.meewii.mentalarithmetic.dagger.qualifiers.ForApplication
import com.meewii.mentalarithmetic.data.database.AppDatabase
import com.meewii.mentalarithmetic.data.database.ScoreDao
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

    @Provides
    @Singleton
    fun provideDatabase(@ForApplication context: Context): AppDatabase =
          Room
          .databaseBuilder(context, AppDatabase::class.java, "MentalArithmetic.db")
          .allowMainThreadQueries()
          .build()

    @Provides
    @Singleton
    fun provideScoreDao(database: AppDatabase): ScoreDao = database.scoreDao()

}