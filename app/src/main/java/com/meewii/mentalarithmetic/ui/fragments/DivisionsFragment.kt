package com.meewii.mentalarithmetic.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.meewii.mentalarithmetic.MainAdapter
import com.meewii.mentalarithmetic.OperationListController
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.ui.activities.SettingsActivity
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
import kotlinx.android.synthetic.main.fragment_operation.*

class DivisionsFragment : Fragment() {

    private var mOperationList: MutableList<Operation>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_operation, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // create an empty list of operation
        mOperationList = mutableListOf()

        // set up list
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity.applicationContext, LinearLayout.VERTICAL, false)
        val mainAdapter = MainAdapter(activity.applicationContext, mOperationList!!)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = mainAdapter

        val preferences = PreferenceManager.getDefaultSharedPreferences(activity.applicationContext)
        val difficultyStr = preferences.getString(SettingsActivity.PREF_LEVEL_DIVISIONS, Difficulty.EASY.toString())
        val difficulty = Difficulty.valueOf(difficultyStr)

        // create controller
        val operationListController = OperationListController (
                activity.applicationContext,
                Operator.DIVISION,
                difficulty,
                mOperationList,
                recyclerView,
                mainAdapter,
                solutionInput,
                currentFormulaView
        )

        // init the calculator
        operationListController.setCalculator()

        // set listener on button
        submitButton.setOnClickListener { operationListController.onSubmitSolution() }
    }
}