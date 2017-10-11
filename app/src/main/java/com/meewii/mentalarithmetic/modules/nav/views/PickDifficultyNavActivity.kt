package com.meewii.mentalarithmetic.modules.nav.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.base.BaseNavActivity
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.modules.game.scored.ScoredGameActivity
import com.meewii.mentalarithmetic.modules.game.training.TrainingGameActivity
import com.meewii.mentalarithmetic.modules.nav.NavAdapter
import dagger.android.AndroidInjection
import javax.inject.Inject

class PickDifficultyNavActivity : BaseNavActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

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
                if(isTraining()) {
                    val intent = Intent(this@PickDifficultyNavActivity, TrainingGameActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@PickDifficultyNavActivity, ScoredGameActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        super.onCreate(savedInstanceState)
    }

    private fun isTraining(): Boolean {
        val gameType = sharedPreferences.getString(Const.GAME_TYPE_EXTRA, null)
        return when(gameType) {
            Const.GAME_TYPE_GAME -> false
            Const.GAME_TYPE_TRAINING -> true
            else -> throw NullPointerException()
        }
    }
}