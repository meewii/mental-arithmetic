package com.meewii.mentalarithmetic.presenters

import android.content.SharedPreferences
import android.text.Editable
import android.util.Log
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.models.Status
import com.meewii.mentalarithmetic.ui.activities.SettingsActivity
import com.meewii.mentalarithmetic.ui.fragments.BaseFragment
import com.meewii.mentalarithmetic.utils.OperandGenerator
import javax.inject.Inject

class MultiplicationsPresenter @Inject constructor(private val sharedPreferences: SharedPreferences) : OperationPresenter {

    private val TAG: String = "MultiplicationsPresenter"

    private lateinit var view: BaseFragment
    private val operator = Operator.MULTIPLICATION

    // Data
    private lateinit var currentOperation: Operation
    val operationList: MutableList<Operation> = mutableListOf()


    override fun init(saySomething: String): MultiplicationsPresenter {
        Log.d(Const.APP_TAG, "$TAG initiated with '$saySomething'")
        return this
    }

    override fun attachView(fragment: BaseFragment): MultiplicationsPresenter {
        view = fragment
        return this
    }

    /**
     * Prepares an operation (2 operands and solution) and display it in views
     */
    override fun generateOperation(): MultiplicationsPresenter {
        val difficultyStr = sharedPreferences.getString(SettingsActivity.PREF_LEVEL_MULTIPLICATIONS, Difficulty.EASY.toString())
        val difficulty = Difficulty.valueOf(difficultyStr)

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
        currentOperation.status =
                when (currentOperation.userSolution) { // same as if(mCurrentOperation?.userSolution == mCurrentOperation?.solution)
                    currentOperation.solution -> Status.SUCCESS
                    else -> Status.FAIL
                }

        // add submitted answer to the list
        Log.d(Const.APP_TAG, "[$TAG#onSubmitSolution] currentOperation: ${currentOperation.getFullUserOperation()} and status: ${currentOperation.status}")
        operationList.add(currentOperation)

        view.updateList()
    }

}