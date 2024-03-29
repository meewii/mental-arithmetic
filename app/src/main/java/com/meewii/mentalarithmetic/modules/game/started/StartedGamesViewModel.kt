package com.meewii.mentalarithmetic.modules.game.started

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.meewii.mentalarithmetic.common.GameRepository
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.models.Difficulty
import javax.inject.Inject


class StartedGamesViewModel @Inject constructor(
        private val gameRepository: GameRepository) : ViewModel() {

    var liveScoreList: MutableLiveData<List<ScoreEntry>> = MutableLiveData()

    fun loadScoreList(difficulty: Difficulty?): MutableLiveData<List<ScoreEntry>> {
        liveScoreList.value = when(difficulty) {
            null -> throw NullPointerException("Difficulty is null")
            else -> gameRepository.getScores(difficulty)
        }

        return liveScoreList
    }

}