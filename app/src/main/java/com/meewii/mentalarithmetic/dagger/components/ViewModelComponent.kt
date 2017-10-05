package com.meewii.mentalarithmetic.dagger.components

import com.meewii.mentalarithmetic.ui.game.GameViewModel
import com.meewii.mentalarithmetic.ui.score.ScoreViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelComponent
    }

    fun gameViewModel(): GameViewModel
    fun scoreViewModel(): ScoreViewModel
}