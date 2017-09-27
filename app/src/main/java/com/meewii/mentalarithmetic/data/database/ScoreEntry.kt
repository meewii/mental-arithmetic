package com.meewii.mentalarithmetic.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.util.Log
import com.meewii.mentalarithmetic.core.Const

@Entity(tableName = "Score")
data class ScoreEntry(
        val difficulty: String,
        var points: Int = 0
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var succeededOp: Int = 0
        set(value) {
            calculatePoints(value)
        }

    var failedOp: Int = 0

    private fun calculatePoints(newPoint: Int) {
        points += newPoint * 5
        Log.d(Const.APP_TAG, "[ScoreEntry#calculatePoints] points: $points")
    }

    override fun toString(): String = "#$id - difficulty: $difficulty - points: $points | success: $succeededOp - fails: $failedOp"
}