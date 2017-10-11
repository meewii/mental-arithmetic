package com.meewii.mentalarithmetic.modules.statistics.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.models.Statistic
import com.meewii.mentalarithmetic.common.GameRepository
import javax.inject.Inject


class StatsViewModel @Inject constructor(
        private val gameRepository: GameRepository) : ViewModel() {

    var liveScoreList: MutableLiveData<List<Statistic>> = MutableLiveData()

    fun loadStats(operator: Operator?): MutableLiveData<List<Statistic>> {

        val statsList = arrayListOf<Statistic>()

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