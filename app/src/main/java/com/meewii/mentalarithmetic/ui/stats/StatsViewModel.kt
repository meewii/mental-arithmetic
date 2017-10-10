package com.meewii.mentalarithmetic.ui.stats

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.ui.game.GameRepository
import javax.inject.Inject


class StatsViewModel @Inject constructor(
        private val gameRepository: GameRepository) : ViewModel() {

    var liveScoreList: MutableLiveData<List<Stat>> = MutableLiveData()

    fun loadStats(operator: Operator?): MutableLiveData<List<Stat>> {

        val statsList = arrayListOf<Stat>()

        for(difficulty in Difficulty.values()) {

            val stat = when(operator) {
                null -> throw NullPointerException("Operator is null")
                else -> gameRepository.getStats(operator, difficulty)
            }
            statsList.add(stat)
        }

        liveScoreList.value = statsList

        return liveScoreList
    }

}