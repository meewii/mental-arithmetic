package com.meewii.mentalarithmetic.ui.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.MainAdapter
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.dagger.components.ActivityComponent
import com.meewii.mentalarithmetic.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.fragment_operation.*

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        @Suppress("UnnecessaryVariable")
        val view = inflater.inflate(R.layout.fragment_operation, container, false)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is BaseActivity) {
            inject(context.activityComponent)
        } else {
            throw Exception("Context is not BaseActivity")
        }
    }




    protected lateinit var mainAdapter: MainAdapter

    protected abstract fun inject(component: ActivityComponent)

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
    open fun resetCalculator() {
        solutionInput.setText("")
    }

    /**
     * Update the RecyclerView with new data
     */
    open fun updateList() {
        mainAdapter.notifyDataSetChanged()
    }
}