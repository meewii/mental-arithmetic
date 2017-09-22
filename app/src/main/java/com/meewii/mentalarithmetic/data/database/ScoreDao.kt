package com.meewii.mentalarithmetic.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ScoreDao {

  @Query("SELECT * FROM score ORDER BY id DESC LIMIT 1")
  fun getLast(): ScoreEntry

  @Query("SELECT * FROM score ORDER BY points DESC LIMIT 1")
  fun getHighest(): ScoreEntry

  @Query("SELECT * FROM score")
  fun getAll(): List<ScoreEntry>

  @Query("SELECT * FROM score WHERE userId=:userId")
  fun getAll(userId: String): List<ScoreEntry>


  @Insert
  fun insert(score: ScoreEntry)

  @Insert
  fun insert(scores: List<ScoreEntry>)

  @Delete
  fun delete(score: ScoreEntry)
}