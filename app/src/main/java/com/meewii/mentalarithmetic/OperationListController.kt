package com.meewii.mentalarithmetic

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.models.Status
import com.meewii.mentalarithmetic.utils.OperandGenerator

/*
Controller of all Operation fragments
*/
class OperationListController(
        private var mContext: Context,
        private val mOperator: Operator,
        private val mDifficulty: Difficulty,
        private var mOperationList: MutableList<Operation>?,
        private val mRecyclerView: RecyclerView,
        private val mMainAdapter: MainAdapter,
        private val mSolutionInput: EditText,
        private val mCurrentFormulaView: TextView) {

    private val TAG: String = "OperationListController"

    private var mCurrentOperation: Operation? = null

    fun onSubmitSolution() {
        val userInputStr: String = mSolutionInput.text.toString().trim()
        Log.v(TAG, "1- userInputStr: $userInputStr")

        // display a warning if the solution input is empty on submit
        if (userInputStr.isEmpty()) {
            mSolutionInput.error = mContext.getString(R.string.error_empty_input)
            return
        }

        // Parse to integer
        mCurrentOperation?.userSolution = Integer.valueOf(userInputStr)

        // check if it's the correct solution
        mCurrentOperation?.status =
                when (mCurrentOperation?.userSolution) { // same as if(mCurrentOperation?.userSolution == mCurrentOperation?.solution)
                    mCurrentOperation?.solution -> Status.SUCCESS
                    else -> Status.FAIL
                }

        // add submitted answer to the list
        val fullUserOperation: String? = mCurrentOperation?.getFullUserOperation()
        val status: Status? = mCurrentOperation?.status
        Log.v(TAG, "2- currentOperation: $fullUserOperation and status: $status")
        if (mCurrentOperation != null) mOperationList?.add(mCurrentOperation!!)
        mMainAdapter.notifyDataSetChanged()

        val pos: Int = mOperationList?.size ?: 1
        mRecyclerView.scrollToPosition(pos - 1)

        // reset current operation
        resetCalculator()
    }

    /**
     * Prepares an operation (2 operands and solution) and display it in views
     * Sets the input to an empty string
     */
    private fun resetCalculator() {
        // reset input
        mSolutionInput.setText("")

        setCalculator()
    }

    /**
     * Prepares an operation (2 operands and solution) and display it in views
     */
    fun setCalculator() {
        // set a new Operation
        val operands: IntArray = OperandGenerator.getOperands(mOperator, mDifficulty)
        Log.i(TAG, "[setCalculator] operands: $operands - operator: $mOperator")
        mCurrentOperation = Operation(mOperator, operands[0], operands[1])

        // display formula in view
        mCurrentFormulaView.text = mCurrentOperation!!.getFormula()
    }

}