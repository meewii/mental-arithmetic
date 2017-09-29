package com.meewii.mentalarithmetic.ui.game

import android.app.Application
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.utils.OperandGenerator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(val application: Application) {

    fun generateOperation(): Operation {
        val operands: IntArray = OperandGenerator.getOperands(Operator.ADDITION, Difficulty.EASY)
        Log.i(Const.APP_TAG, "[GameValidator#generateOperation] operands: ${operands[0]} | ${operands[1]}")
        return Operation(Operator.ADDITION, operands[0], operands[1])
    }

    fun generateOperationList(): ArrayList<Operation> = arrayListOf()

}