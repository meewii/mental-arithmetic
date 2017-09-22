package com.meewii.mentalarithmetic.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Score")
data class ScoreEntry(
    val difficulty: String,
    val points: Int,
    val userId: String = "meewii"
) {

    @PrimaryKey(autoGenerate = true) var id: Int? = null

    override fun toString(): String = "#$id - difficulty: $difficulty - points: $points"
}