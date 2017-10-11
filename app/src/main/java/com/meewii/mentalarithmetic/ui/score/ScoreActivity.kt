package com.meewii.mentalarithmetic.ui.score

import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.ui.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : BaseActivity() {

    override fun getLifecycle(): LifecycleRegistry = LifecycleRegistry(this@ScoreActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Create the adapter that will return a fragment for each of the 5
        // primary sections of the activity.
        val scorePagerAdapter = ScorePagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        viewPager!!.adapter = scorePagerAdapter
    }

}