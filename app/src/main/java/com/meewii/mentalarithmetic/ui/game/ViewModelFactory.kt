package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.ArrayMap
import com.meewii.mentalarithmetic.dagger.components.ViewModelComponent
import kotlin.reflect.KFunction

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(viewModelComponent: ViewModelComponent) : ViewModelProvider.Factory {
    private var creators: ArrayMap<Class<*>, KFunction<ViewModel>> = ArrayMap()

    init {
        creators.put(GameViewModel::class.java, viewModelComponent::gameViewModel)
        // Put other view model declarations here
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

        try {
            return creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}