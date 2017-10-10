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
class GameRepository @Inject constructor(
        val application: Application,
        private val scoreDao: ScoreDao) {

    private val operationList: ArrayList<Operation> = arrayListOf()

    fun generateOperation(operator: Operator, difficulty: Difficulty): Operation {
        val operands: IntArray = OperandGenerator.getOperands(operator, difficulty)
        return Operation(operator, operands[0], operands[1])
    }



    fun generateScore(operator: Operator, difficulty: Difficulty): ScoreEntry {
        var savedScore = ScoreEntry(
                operator = operator,
                difficulty = difficulty,
                created_at = System.currentTimeMillis(),
                user_id = 1)

        val rowId = scoreDao.insert(savedScore)

        if(rowId > 0) {
            savedScore = scoreDao.getRow(rowId)
        } else {
            Log.e(Const.APP_TAG, "[GameRepository#generateScore] Error while upserting Score")
        }

        return savedScore
    }

    fun saveScore(score: ScoreEntry) = scoreDao.insert(score)

    fun getScores(difficulty: Difficulty): List<ScoreEntry>? =
            scoreDao.getAllWithDifficulty(difficulty)




    fun newOperationList(): ArrayList<Operation> {
        operationList.clear()
        return operationList
    }

    fun getOperationList(): ArrayList<Operation> = operationList

    fun addOperationToList(operation: Operation) {
        operationList.add(operation)
    }

}