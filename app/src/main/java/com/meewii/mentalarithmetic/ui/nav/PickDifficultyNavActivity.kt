package com.meewii.mentalarithmetic.ui.nav

import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.ui.game.GameActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class PickDifficultyNavActivity : BaseNavActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val registry = LifecycleRegistry(this)
    override fun getLifecycle(): LifecycleRegistry = registry

    override fun onCreate(savedInstanceState: Bundle?) {
        //DI
        AndroidInjection.inject(this)

        // Get string array from xml resource file
        navItems = resources.getStringArray(R.array.difficulties_menu)

        // Create click listener specific to this string array
        listener = object : NavAdapter.OnItemClickListener {
            override fun onItemClick(item: String) {

                val difficulty: Difficulty = when {
                    item.contentEquals(navItems[0]) -> Difficulty.VERY_EASY
                    item.contentEquals(navItems[1]) -> Difficulty.EASY
                    item.contentEquals(navItems[2]) -> Difficulty.MEDIUM
                    item.contentEquals(navItems[3]) -> Difficulty.HARD
                    item.contentEquals(navItems[4]) -> Difficulty.VERY_HARD
                    else -> {
                        throw IllegalArgumentException("Item $item not found in Difficulty Menu")
                    }
                }

                // Save user's choice in Preferences
                sharedPreferences
                        .edit()
                        .putString(Const.DIFFICULTY_EXTRA, difficulty.toString())
                        .apply()

                // Go to next activity
                val intent = Intent(this@PickDifficultyNavActivity, GameActivity::class.java)
                startActivity(intent)
            }
        }

        super.onCreate(savedInstanceState)
    }
}