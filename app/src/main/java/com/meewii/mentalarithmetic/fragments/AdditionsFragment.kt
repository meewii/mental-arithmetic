package com.meewii.mentalarithmetic.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.MainAdapter
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.models.Operation
import kotlinx.android.synthetic.main.fragment_additions.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.meewii.mentalarithmetic.models.Status
import com.meewii.mentalarithmetic.models.Operator
import java.util.*

class AdditionsFragment : Fragment() {

    private val TAG: String = "AdditionsFragment"

    private var mMainAdapter: MainAdapter? = null
    private var mOperationList: MutableList<Operation>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mCurrentOperation: Operation? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_additions, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentFormula.setText(R.string.additions)

        initialize()
        resetCalculator()
        setupList()
    }

    private fun initialize() {
        mOperationList = mutableListOf()
        mLayoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        mMainAdapter = MainAdapter(context, mOperationList!!)
        submitButton.setOnClickListener { submitSolution() }
    }

    private fun setupList() {
        pastFormulaList!!.layoutManager = mLayoutManager
        pastFormulaList!!.scrollToPosition(mOperationList!!.size - 1)
        pastFormulaList!!.adapter = mMainAdapter
    }

    private fun submitSolution() {
        val inputStr: String = solutionInput.text.toString().trim()
        Log.v(TAG, "1- inputStr: $inputStr")

        // display a warning if the solution input is empty on submit
        if(inputStr.isEmpty()) {
            solutionInput.error = getString(R.string.error_empty_input)
            return
        }

        // get submitted input
        val inputNb: Int = Integer.valueOf(inputStr)
        mCurrentOperation!!.userSolution = inputNb

        // check if it's correct
        if(inputNb == mCurrentOperation!!.solution) {
            mCurrentOperation!!.status = Status.SUCCESS
        } else {
            mCurrentOperation!!.status = Status.FAIL
        }

        // add submitted answer to the list
        val fullUserOperation: String = mCurrentOperation!!.getFullUserOperation()
        val status: Status = mCurrentOperation!!.status
        Log.v(TAG, "2- currentOperation: $fullUserOperation and status: $status")
        mOperationList!!.add(mCurrentOperation!!)
        mMainAdapter!!.notifyDataSetChanged()
        pastFormulaList!!.scrollToPosition(mOperationList!!.size - 1)

        // reset current operation
        resetCalculator()
    }

    /**
     * Prepares an operation (2 operands and solution) and display it in views
     * Sets the input to an empty string
     */
    private fun resetCalculator() {
        // reset input
        solutionInput.setText("")

        // set a new Operation
        val operandA: Int = Random().nextInt(12 - 1) + 1
        val operandB: Int  = Random().nextInt(12 - 1) + 1
        mCurrentOperation = Operation(Operator.ADDITION, operandA, operandB)

        // display formula in view
        currentFormula.text = mCurrentOperation!!.getFormula()
    }

}