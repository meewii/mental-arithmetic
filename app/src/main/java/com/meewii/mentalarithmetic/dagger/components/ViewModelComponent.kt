package com.meewii.mentalarithmetic.dagger.components

import com.meewii.mentalarithmetic.modules.game.scored.ScoredGameViewModel
import com.meewii.mentalarithmetic.modules.game.started.StartedGamesViewModel
import com.meewii.mentalarithmetic.modules.game.training.TrainingGameViewModel
import com.meewii.mentalarithmetic.modules.statistics.viewmodels.StatsViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelComponent
    }

    fun trainingGameViewModel(): TrainingGameViewModel
    fun scoredGameViewModel(): ScoredGameViewModel
    fun statsViewModel(): StatsViewModel
}