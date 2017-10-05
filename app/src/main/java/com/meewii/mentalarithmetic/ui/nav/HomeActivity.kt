package com.meewii.mentalarithmetic.ui.nav

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.ui.activities.CreditsActivity
import com.meewii.mentalarithmetic.ui.score.ScoreActivity
import dagger.android.AndroidInjection

class HomeActivity : BaseNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        navItems = resources.getStringArray(R.array.new_game_menu)

        listener = object : NavAdapter.OnItemClickListener {
            override fun onItemClick(item: String) {

                when {
                    item.contentEquals(navItems[0]) -> {
                        Log.d(Const.APP_TAG, "Selected ${navItems[0]}")
                        val intent = Intent(this@HomeActivity, PickOperationTypeNavActivity::class.java)
                        startActivity(intent)
                    }
                    item.contentEquals(navItems[1]) -> {
                        val intent = Intent(this@HomeActivity, ScoreActivity::class.java)
                        startActivity(intent)
                    }
                    item.contentEquals(navItems[2]) -> {
                        val intent = Intent(this@HomeActivity, CreditsActivity::class.java)
                        startActivity(intent)
                    }
                    else -> Toast.makeText(applicationContext, "Error: $item doesn't exist!", Toast.LENGTH_LONG).show()
                }
            }
        }

        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
