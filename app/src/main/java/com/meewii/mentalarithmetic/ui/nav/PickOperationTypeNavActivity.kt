package com.meewii.mentalarithmetic.ui.nav

import android.content.Intent
import android.os.Bundle
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Operator

class PickOperationTypeNavActivity : BaseNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        navItems = resources.getStringArray(R.array.operation_type_menu)

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