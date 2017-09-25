package com.meewii.mentalarithmetic.presenters

import android.content.SharedPreferences
import android.support.design.widget.Snackbar
import android.text.Editable
import android.util.Log
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.data.database.ScoreDao
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.models.Status
import com.meewii.mentalarithmetic.ui.activities.SettingsActivity
import com.meewii.mentalarithmetic.ui.fragments.BaseFragment
import com.meewii.mentalarithmetic.utils.OperandGenerator
import kotlinx.android.synthetic.main.fragment_operation.*
import javax.inject.Inject

class AdditionsPresenter @Inject constructor(sharedPreferences: SharedPreferences,
                                             private val scoreDao: ScoreDao) : OperationPresenter {

    private val TAG: String = "AdditionsPresenter"

    private val FAIL_LIMIT: Int = 5
    private lateinit var view: BaseFragment
    private lateinit var score: ScoreEntry
    private val difficulty: Difficulty
    private val operator = Operator.ADDITION
    private lateinit var currentOperation: Operation
    val operationList: MutableList<Operation> = mutableListOf()

    // constructor
    init {
        val difficultyStr = sharedPreferences.getString(SettingsActivity.PREF_LEVEL_ADDITIONS, Difficulty.EASY.toString())
        difficulty = Difficulty.valueOf(difficultyStr)
        Log.d(Const.APP_TAG, "[$TAG#init] difficulty '$difficulty'")
    }



    override fun attachView(fragment: BaseFragment): AdditionsPresenter {
        view = fragment
        return this
    }

    override fun newGame(): AdditionsPresenter {
        // init score
        score = ScoreEntry(difficulty.toString())
        // start new list
        operationList.clear()
        view.updateList()
        // start new game in view
        generateOperation()
        view.newOperation()

        view.showSoftKeyboard()
        return this
    }

    /**
     * Prepares an operation (2 operands and solution) and display it in views
     */
    override fun generateOperation(): AdditionsPresenter {
        val operands: IntArray = OperandGenerator.getOperands(operator, difficulty)
        Log.i(Const.APP_TAG, "[$TAG#generateOperation] operands: ${operands[0]} | ${operands[1]} - operator: $operator")
        currentOperation = Operation(operator, operands[0], operands[1])

        // display formula in view
        view.displayFormula(currentOperation.getFormula())
        return this
    }


    override fun onSubmitSolution(submittedSolution: Editable) {
        val userInputStr: String = submittedSolution.toString().trim()
        Log.d(Const.APP_TAG, "[$TAG#onSubmitSolution] userInputStr: $userInputStr")

        // display a warning if the solution input is empty on submit
        if (userInputStr.isEmpty()) {
            view.displayError(R.string.error_empty_input)
            return
        }

        // Parse to integer
        currentOperation.userSolution = Integer.valueOf(userInputStr)

        // check if it's the correct solution
        if(currentOperation.userSolution == currentOperation.solution) {
            currentOperation.status = Status.SUCCESS
            score.succeededOp += 1
        } else {
            currentOperation.status = Status.FAIL
            score.failedOp += 1
        }

        // add submitted answer to the list
        Log.d(Const.APP_TAG, "[$TAG#onSubmitSolution] currentOperation: ${currentOperation.getFullUserOperation()} and status: ${currentOperation.status}")
        operationList.add(currentOperation)
        view.updateList()

        // if the gamer failed n times, the game ends
        if(score.failedOp >= FAIL_LIMIT) {
            saveScore()
            view.disableGame()

            val gameOverBar = Snackbar
                    .make(view.container, "Game over! Points: ${score.points}", Snackbar.LENGTH_INDEFINITE)
                    .setAction("New game?") {
                        newGame()
                    }
            gameOverBar.show()
        } else {
            view.newOperation()
        }

    }

    override fun saveScore() {
        scoreDao.insert(score)
    }
}