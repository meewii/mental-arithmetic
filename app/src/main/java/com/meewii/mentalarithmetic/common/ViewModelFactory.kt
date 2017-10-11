package com.meewii.mentalarithmetic.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.ArrayMap
import com.meewii.mentalarithmetic.dagger.components.ViewModelComponent
import com.meewii.mentalarithmetic.modules.game.scored.ScoredGameViewModel
import com.meewii.mentalarithmetic.modules.game.started.StartedGamesViewModel
import com.meewii.mentalarithmetic.modules.game.training.TrainingGameViewModel
import com.meewii.mentalarithmetic.modules.statistics.viewmodels.StatsViewModel
import kotlin.reflect.KFunction

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(viewModelComponent: ViewModelComponent) : ViewModelProvider.Factory {
    private var creators: ArrayMap<Class<*>, KFunction<ViewModel>> = ArrayMap()

    init {
        creators.put(ScoredGameViewModel::class.java, viewModelComponent::scoredGameViewModel)
        creators.put(TrainingGameViewModel::class.java, viewModelComponent::trainingGameViewModel)
        creators.put(StatsViewModel::class.java, viewModelComponent::statsViewModel)
        creators.put(StartedGamesViewModel::class.java, viewModelComponent::startedGamesViewModel)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: KFunction<ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        creator ?: throw IllegalArgumentException("unknown model class " + modelClass)

        return try {
            creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}