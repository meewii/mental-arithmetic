package com.meewii.mentalarithmetic.ui.stats

import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.ui.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_stats.*

class StatsActivity : BaseActivity() {

    override fun getLifecycle(): LifecycleRegistry = LifecycleRegistry(this@StatsActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Create the adapter that will return a fragment for each of the 4
        // primary sections of the activity.
        val scorePagerAdapter = StatsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        viewPager!!.adapter = scorePagerAdapter
    }

}