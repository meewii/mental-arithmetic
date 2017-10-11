package com.meewii.mentalarithmetic.dagger.modules

import android.app.Application
import android.arch.persistence.room.Room
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.meewii.mentalarithmetic.data.database.AppDatabase
import com.meewii.mentalarithmetic.data.database.ScoreDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase =
            Room
                    .databaseBuilder(application, AppDatabase::class.java, "MentalArithmetic.db")
                    .allowMainThreadQueries()
                    .build()

    @Provides
    @Singleton
    fun provideScoreDao(database: AppDatabase): ScoreDao = database.scoreDao()

    @Provides
    @Singleton
    internal fun provideSharedPreferences(application: Application): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(application)

}