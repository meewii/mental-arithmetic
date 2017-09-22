package com.meewii.mentalarithmetic.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(ScoreEntry::class), version = 2)
abstract class AppDatabase: RoomDatabase() {

  abstract fun scoreDao(): ScoreDao

}
