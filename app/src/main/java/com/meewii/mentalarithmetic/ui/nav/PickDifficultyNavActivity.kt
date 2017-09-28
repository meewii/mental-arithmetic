package com.meewii.mentalarithmetic.ui.nav

import android.content.Intent
import android.os.Bundle
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.ui.operations.OperationActivity

class PickDifficultyNavActivity : BaseNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val operator = intent.extras.getString(Const.OPERATOR_TYPE_EXTRA)

        navItems = resources.getStringArray(R.array.difficulties_menu)

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

                val intent = Intent(this@PickDifficultyNavActivity, OperationActivity::class.java)
                intent.putExtra(Const.OPERATOR_TYPE_EXTRA, operator)
                intent.putExtra(Const.DIFFICULTY_EXTRA, difficulty.name)
                startActivity(intent)
            }
        }

        super.onCreate(savedInstanceState)
    }
}