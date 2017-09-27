package com.meewii.mentalarithmetic.ui.activities

import android.os.Bundle
import android.util.Log
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.ui.fragments.OperationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        activityComponent.inject(this)

        val operator = intent.extras.getString(Const.OPERATOR_TYPE_EXTRA)
        val difficulty = intent.extras.getString(Const.DIFFICULTY_EXTRA)
        Log.i("MainActivity", "Operator: $operator - Difficulty: $difficulty")

        val fragment = OperationFragment()
        fragment.arguments = intent.extras

        // load fragment
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }

}