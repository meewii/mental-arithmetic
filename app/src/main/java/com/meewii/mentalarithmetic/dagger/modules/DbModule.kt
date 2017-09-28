package com.meewii.mentalarithmetic.dagger.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.meewii.mentalarithmetic.dagger.qualifiers.ForApplication
import com.meewii.mentalarithmetic.data.database.AppDatabase
import com.meewii.mentalarithmetic.data.database.ScoreDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

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