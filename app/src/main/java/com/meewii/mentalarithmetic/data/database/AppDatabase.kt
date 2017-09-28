package com.meewii.mentalarithmetic.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = arrayOf(ScoreEntry::class), version = 5, exportSchema = false)
@TypeConverters(ScoreConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun scoreDao(): ScoreDao

}
