package com.meewii.mentalarithmetic.ui.game

import android.content.SharedPreferences
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.utils.OperandGenerator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameController @Inject constructor(sharedPreferences: SharedPreferences) {

    private val difficulty: Difficulty
    private val operator: Operator

    init {
        val diff = sharedPreferences.getString(Const.DIFFICULTY_EXTRA, Difficulty.EASY.toString())
        val oper = sharedPreferences.getString(Const.OPERATOR_TYPE_EXTRA, Operator.ADDITION.toString())
        Log.v(Const.APP_TAG, "[GameValidator#init] diff: $diff | oper: $oper")
        difficulty = Difficulty.valueOf(diff)
        operator = Operator.valueOf(oper)
    }

    fun generateOperation(): Operation {
        val operands: IntArray = OperandGenerator.getOperands(operator, difficulty)
        Log.i(Const.APP_TAG, "[GameValidator#generateOperation] operands: ${operands[0]} | ${operands[1]}")
        return Operation(operator, operands[0], operands[1])
    }

//    private val FAIL_LIMIT: Int = 5
//    private lateinit var score: ScoreEntry
//
//    fun newGame() {
//        // init score
//        score = ScoreEntry(operator = operator, difficulty = difficulty, created_at = System.currentTimeMillis(), user_id = 1)
//        // start new list
//        operationList.clear()
//        view.updateList()
//        // start new game in view
//        generateOperation()
//        view.newOperation()
//
//        view.showSoftKeyboard()
//    }
//
//    /**
//     * Prepares an operation (2 operands and solution) and display it in views
//     */
//    fun generateOperation() {
//        val operands: IntArray = OperandGenerator.getOperands(operator, difficulty)
//        Log.i(Const.APP_TAG, "[$TAG#generateOperation] operands: ${operands[0]} | ${operands[1]} - operator: $operator")
//        currentOperation = Operation(operator, operands[0], operands[1])
//
//        // display formula in view
//        view.displayFormula(currentOperation.getFormula())
//    }
//
//
//    fun submitSolution(submittedSolution: Editable) {
//        val userInputStr: String = submittedSolution.toString().trim()
//        Log.d(Const.APP_TAG, "[#onSubmitSolution] userInputStr: $userInputStr")
//
//        // display a warning if the solution input is empty on submit
//        if (userInputStr.isEmpty()) {
//            view.displayError(R.string.error_empty_input)
//            return
//        }
//
//        // Parse to integer
//        currentOperation.userSolution = Integer.valueOf(userInputStr)
//
//        // check if it's the correct solution
//        if (currentOperation.userSolution == currentOperation.solution) {
//            currentOperation.status = Status.SUCCESS
//            score.succeededOp += 1
//        } else {
//            currentOperation.status = Status.FAIL
//            score.failedOp += 1
//        }
//
//        // add submitted answer to the list
//        Log.d(Const.APP_TAG, "[$TAG#onSubmitSolution] currentOperation: ${currentOperation.getFullUserOperation()} and status: ${currentOperation.status}")
//        operationList.add(currentOperation)
//        view.updateList()
//
//        // if the gamer failed n times, the game ends
//        if (score.failedOp >= FAIL_LIMIT) {
//            saveScore()
//            view.disableGame()
//
//            val gameOverBar = Snackbar
//                    .make(view.container, "Game over! Points: ${score.points}", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("New game?") {
//                        newGame()
//                    }
//            gameOverBar.show()
//        } else {
//            view.newOperation()
//        }
//
//    }
//
//    private fun saveScore() {
//        // save the duration
//        // TODO: timer system
//        score.updated_at = System.currentTimeMillis()
//        scoreDao.insert(score)
//    }
}