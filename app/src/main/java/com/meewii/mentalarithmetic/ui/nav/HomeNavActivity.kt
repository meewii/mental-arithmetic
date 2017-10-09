package com.meewii.mentalarithmetic.ui.nav

import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.core.Const.GAME_TYPE_GAME
import com.meewii.mentalarithmetic.core.Const.GAME_TYPE_TRAINING
import com.meewii.mentalarithmetic.ui.activities.CreditsActivity
import com.meewii.mentalarithmetic.ui.score.ScoreActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeNavActivity : BaseNavActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

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
                    sharedPreferences.edit().putString(Const.GAME_TYPE_EXTRA, GAME_TYPE_GAME).apply()
                    val intent = Intent(this@HomeNavActivity, PickOperatorNavActivity::class.java)
                    startActivity(intent)
                }
                item.contentEquals(navItems[1]) -> {
                    sharedPreferences.edit().putString(Const.GAME_TYPE_EXTRA, GAME_TYPE_TRAINING).apply()
                    val intent = Intent(this@HomeNavActivity, PickOperatorNavActivity::class.java)
                    startActivity(intent)
                }
                item.contentEquals(navItems[2]) -> {
                    val intent = Intent(this@HomeNavActivity, ScoreActivity::class.java)
                    startActivity(intent)
                }
                item.contentEquals(navItems[3]) -> {
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
