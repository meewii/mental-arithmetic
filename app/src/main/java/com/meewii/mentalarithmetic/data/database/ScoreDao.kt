package com.meewii.mentalarithmetic.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.meewii.mentalarithmetic.models.Difficulty

@Dao
interface ScoreDao {

    @Query("SELECT * FROM scores ORDER BY id DESC LIMIT 1")
    fun getLast(): ScoreEntry

    @Query("SELECT * FROM scores ORDER BY points DESC LIMIT 1")
    fun getHighest(): ScoreEntry

    @Query("SELECT * FROM scores")
    fun getAll(): List<ScoreEntry>

    @Query("SELECT * FROM scores ORDER BY points DESC")
    fun getAllOrderByPointsDesc(): List<ScoreEntry>

    @Query("SELECT * FROM scores WHERE difficulty = :difficulty ORDER BY points DESC")
    fun getAllWithDifficulty(difficulty: Difficulty): List<ScoreEntry>

    @Insert
    fun insert(score: ScoreEntry)

    @Insert
    fun insert(scores: List<ScoreEntry>)

    @Delete
    fun delete(score: ScoreEntry)
}