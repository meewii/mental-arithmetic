package com.meewii.mentalarithmetic.ui.game

import android.app.Application
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.data.database.ScoreDao
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.utils.OperandGenerator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(val application: Application, private val scoreDao: ScoreDao) {

    private val operationList: ArrayList<Operation> = arrayListOf()

    // TODO: get real values for Operator and Difficulty
    private val operator = Operator.ADDITION
    private val difficulty = Difficulty.EASY

    fun generateOperation(): Operation {
        val operands: IntArray = OperandGenerator.getOperands(operator, difficulty)
        return Operation(Operator.ADDITION, operands[0], operands[1])
    }

    fun generateScore(): ScoreEntry =
            ScoreEntry(
                    operator = operator,
                    difficulty = difficulty,
                    created_at = System.currentTimeMillis(),
                    user_id = 1)


    fun newOperationList(): ArrayList<Operation> {
        operationList.clear()
        return operationList
    }

    fun getOperationList(): ArrayList<Operation> = operationList

    fun addOperationToList(operation: Operation) {
        operationList.add(operation)
    }

    fun saveScore(score: ScoreEntry) {
        scoreDao.insert(score)
    }

}