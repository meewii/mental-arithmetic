package com.meewii.mentalarithmetic.modules.nav.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.base.BaseNavActivity
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.modules.nav.NavAdapter
import dagger.android.AndroidInjection
import javax.inject.Inject

class PickOperatorNavActivity : BaseNavActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        //DI
        AndroidInjection.inject(this)

        // Get string array from xml resource file
        navItems = resources.getStringArray(R.array.operation_type_menu)

        // Create click listener specific to this string array
        listener = object : NavAdapter.OnItemClickListener {
            override fun onItemClick(item: String) {

                val operator: Operator = when {
                    item.contentEquals(navItems[0]) -> Operator.ADDITION
                    item.contentEquals(navItems[1]) -> Operator.SUBTRACTION
                    item.contentEquals(navItems[2]) -> Operator.MULTIPLICATION
                    item.contentEquals(navItems[3]) -> Operator.DIVISION
                    else -> {
                        throw IllegalArgumentException("Item $item not found in Operation Type Menu")
                    }
                }

                // Save user's choice in Preferences
                sharedPreferences
                        .edit()
                        .putString(Const.OPERATOR_TYPE_EXTRA, operator.toString())
                        .apply()

                // Go to next activity
                val intent = Intent(this@PickOperatorNavActivity, PickDifficultyNavActivity::class.java)
                startActivity(intent)
            }
        }

        super.onCreate(savedInstanceState)
    }
}