package com.meewii.mentalarithmetic.modules.staticpage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.meewii.mentalarithmetic.R
import kotlinx.android.synthetic.main.activity_credits.*

class CreditsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

        // toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}