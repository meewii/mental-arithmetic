package com.meewii.mentalarithmetic.ui.nav

import android.arch.lifecycle.LifecycleRegistry
import android.content.Intent
import android.os.Bundle
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Operator
import dagger.android.AndroidInjection

class PickOperationTypeNavActivity : BaseNavActivity() {

    // Make this Activity a LifecycleOwner
    override fun getLifecycle(): LifecycleRegistry = LifecycleRegistry(this@PickOperationTypeNavActivity)

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

                val intent = Intent(this@PickOperationTypeNavActivity, PickDifficultyNavActivity::class.java)
                intent.putExtra(Const.OPERATOR_TYPE_EXTRA, operator.name)
                startActivity(intent)
            }
        }

        super.onCreate(savedInstanceState)
    }
}