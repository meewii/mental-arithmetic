package com.meewii.mentalarithmetic.ui.score

import com.meewii.mentalarithmetic.data.database.ScoreDao
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import javax.inject.Inject

class ScorePresenter @Inject
constructor(private val scoreDao: ScoreDao) {

    private lateinit var view: ScoreActivity

    fun attachView(activity: ScoreActivity): ScorePresenter {
        view = activity
        return this
    }

    fun getScores(): List<ScoreEntry> = scoreDao.getAllOrderByPointsDesc()

}