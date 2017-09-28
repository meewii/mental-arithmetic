package com.meewii.mentalarithmetic.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operator

@Entity(tableName = "scores")
data class ScoreEntry(
        val difficulty: Difficulty,
        val operator: Operator,
        var points: Int = 0,
        var created_at: Long = 0,
        var updated_at: Long = 0,
        var user_id: Int = -1 // TODO: the Presenter doesn't provide userId for now
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @Ignore
    var succeededOp: Int = 0
        set(value) {
            calculatePoints(value)
        }
    @Ignore
    var failedOp: Int = 0

    private fun calculatePoints(newPoint: Int) {
        points += newPoint * 5
        Log.d(Const.APP_TAG, "[ScoreEntry#calculatePoints] points: $points")
    }

    override fun toString(): String =
            "#$id - difficulty: $difficulty - operator: $operator - points: $points " +
                    "| success: $succeededOp - fails: $failedOp " +
                    "| created_at: $created_at - updated_at: $updated_at"

}