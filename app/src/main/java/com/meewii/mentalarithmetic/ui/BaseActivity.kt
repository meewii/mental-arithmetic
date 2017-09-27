package com.meewii.mentalarithmetic.ui

import android.support.v7.app.AppCompatActivity
import com.meewii.mentalarithmetic.core.App
import com.meewii.mentalarithmetic.dagger.components.ActivityComponent

abstract class BaseActivity: AppCompatActivity() {

    val activityComponent: ActivityComponent by lazy { (applicationContext as App).activityComponent(this) }

}