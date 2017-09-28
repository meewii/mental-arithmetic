package com.meewii.mentalarithmetic.dagger.components

import com.meewii.mentalarithmetic.ui.game.GameViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelComponent
    }

    fun gameViewModel(): GameViewModel
}