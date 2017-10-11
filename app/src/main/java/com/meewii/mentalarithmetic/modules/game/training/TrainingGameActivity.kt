package com.meewii.mentalarithmetic.modules.game.training

import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import com.meewii.mentalarithmetic.base.BaseGameActivity
import dagger.android.AndroidInjection

class TrainingGameActivity: BaseGameActivity() {

    private val gameViewModel by lazy { getViewModel(TrainingGameViewModel::class.java) as TrainingGameViewModel }

    private val registry = LifecycleRegistry(this)
    override fun getLifecycle(): LifecycleRegistry = registry

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpView(gameViewModel)
        startTimer(gameViewModel)

        observeLiveCurrentOperation(gameViewModel)
        observeLiveOperationList(gameViewModel)
        observeLiveEditTextState(gameViewModel)
        observeLiveGameDuration(gameViewModel)
    }

}