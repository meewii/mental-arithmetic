package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
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

        observeLiveCurrentOperation(gameViewModel)
        observeLiveOperationList(gameViewModel)
        observeLiveEditTextState(gameViewModel)
    }

}