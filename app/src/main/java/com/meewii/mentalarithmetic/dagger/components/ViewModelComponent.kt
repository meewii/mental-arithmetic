package com.meewii.mentalarithmetic.dagger.components

import com.meewii.mentalarithmetic.ui.game.ScoredGameViewModel
import com.meewii.mentalarithmetic.ui.game.TrainingGameViewModel
import com.meewii.mentalarithmetic.ui.score.ScoreViewModel
import com.meewii.mentalarithmetic.ui.stats.StatsViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelComponent
    }

    fun trainingGameViewModel(): TrainingGameViewModel
    fun scoredGameViewModel(): ScoredGameViewModel
    fun scoreViewModel(): ScoreViewModel
    fun statsViewModel(): StatsViewModel
}