package com.meewii.mentalarithmetic.ui.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.dagger.components.ActivityComponent
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.ui.OperationAdapter
import com.meewii.mentalarithmetic.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.fragment_operation.*
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        @Suppress("UnnecessaryVariable")
        val view = inflater.inflate(R.layout.fragment_operation, container, false)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            inject(context.activityComponent)
        } else {
            throw Exception("Context is not BaseActivity")
        }
    }


    @Inject
    protected lateinit var layoutManager: LinearLayoutManager
    @Inject
    protected lateinit var operationAdapter: OperationAdapter

    /**
     * Display current formula in the view
     */
    protected fun setUpAdapter(operationList: MutableList<Operation>) {
        recyclerView.adapter = operationAdapter.addOperations(operationList)
        recyclerView.layoutManager = layoutManager
    }

    /**
     * Display current formula in the view
     */
    fun displayFormula(formula: String) {
        currentFormulaView.text = formula
    }

    /**
     * Display an error on the input field
     */
    fun displayError(errMessageId: Int) {
        solutionInput.error = getString(errMessageId)
    }

    /**
     * Sets the input to an empty string
     * and generates a new formula
     */
    open fun newOperation() {
        solutionInput.setText("")
        inputContainer.visibility = View.VISIBLE
    }

    /**
     * Display information to end the game
     */
    open fun disableGame() {
        inputContainer.visibility = View.INVISIBLE
    }

    /**
     * Update the RecyclerView with new data
     */
    abstract fun updateList()

    protected abstract fun inject(component: ActivityComponent)
}