package com.meewii.mentalarithmetic.models

import com.meewii.mentalarithmetic.data.database.ScoreEntry
import java.util.concurrent.TimeUnit

data class Statistic(
        val difficulty: Difficulty,
        val gameCount: Int
        ) {

    var fastestDuration: String = "00:00:00"
    var averageDuration: String = "00:00:00"
    var slowestDuration: String = "00:00:00"

    fun calculateTimes(list: List<ScoreEntry>) {
        var durationCount = 0
        var durationTotal: Long = 0
        var fastest: Long = 0
        var slowest: Long = 0

        for (score in list) {
            durationTotal += score.duration
            durationCount += 1

            if(fastest > score.duration) {
                fastest = score.duration
                fastestDuration = getDurationString(score.duration)
            }
            if(slowest < score.duration) {
                slowest = score.duration
                slowestDuration = getDurationString(score.duration)
            }
        }

        if(durationCount != 0 && durationTotal != 0L) {
            averageDuration = getDurationString(durationTotal/durationCount)
        }
    }


    private fun getDurationString(duration: Long = 0): String {
        return String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(duration) % TimeUnit.MINUTES.toSeconds(1)
        )
    }

}