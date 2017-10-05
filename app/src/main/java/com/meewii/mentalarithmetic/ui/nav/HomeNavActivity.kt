package com.meewii.mentalarithmetic.ui.nav

import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.ui.activities.CreditsActivity
import com.meewii.mentalarithmetic.ui.score.ScoreActivity
import dagger.android.AndroidInjection

class HomeNavActivity : BaseNavActivity() {

    private val registry = LifecycleRegistry(this)
    override fun getLifecycle(): LifecycleRegistry = registry

    override fun onCreate(savedInstanceState: Bundle?) {
        //DI
        AndroidInjection.inject(this)

        // Get string array from xml resource file
        navItems = resources.getStringArray(R.array.new_game_menu)

        // Create click listener specific to this string array
        listener = object : NavAdapter.OnItemClickListener {
            override fun onItemClick(item: String) = when {
                item.contentEquals(navItems[0]) -> {
                    val intent = Intent(this@HomeNavActivity, PickOperatorNavActivity::class.java)
                    startActivity(intent)
                }
                item.contentEquals(navItems[1]) -> {
                    val intent = Intent(this@HomeNavActivity, ScoreActivity::class.java)
                    startActivity(intent)
                }
                item.contentEquals(navItems[2]) -> {
                    val intent = Intent(this@HomeNavActivity, CreditsActivity::class.java)
                    startActivity(intent)
                }
                else -> Toast.makeText(applicationContext, "Error: $item doesn't exist!", Toast.LENGTH_LONG).show()
            }
        }

        // BaseNavActivity.onCreate()
        super.onCreate(savedInstanceState)

        // Do not show back-arrow for this page
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
