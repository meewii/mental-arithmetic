package com.meewii.mentalarithmetic.ui.score

import com.meewii.mentalarithmetic.data.database.ScoreDao
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.models.Difficulty
import javax.inject.Inject

class ScorePresenter @Inject
constructor(private val scoreDao: ScoreDao) {

    private lateinit var view: ScoreActivity.ScoreFragment

    fun attachView(fragment: ScoreActivity.ScoreFragment): ScorePresenter {
        view = fragment
        return this
    }

    fun getScoresVeryEasy(): List<ScoreEntry> = scoreDao.getAllWithDifficulty(Difficulty.VERY_EASY)
    fun getScoresEasy(): List<ScoreEntry> = scoreDao.getAllWithDifficulty(Difficulty.EASY)
    fun getScoresMedium(): List<ScoreEntry> = scoreDao.getAllWithDifficulty(Difficulty.MEDIUM)
    fun getScoresHard(): List<ScoreEntry> = scoreDao.getAllWithDifficulty(Difficulty.HARD)
    fun getScoresVeryHard(): List<ScoreEntry> = scoreDao.getAllWithDifficulty(Difficulty.VERY_HARD)
}