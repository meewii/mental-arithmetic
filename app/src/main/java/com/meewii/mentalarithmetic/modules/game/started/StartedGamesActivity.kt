package com.meewii.mentalarithmetic.modules.game.started

import android.os.Bundle
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.base.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_started_games.*

class StartedGamesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_started_games)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

}