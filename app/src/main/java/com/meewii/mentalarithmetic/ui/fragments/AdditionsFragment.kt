package com.meewii.mentalarithmetic.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.MainAdapter
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.models.Operation
import kotlinx.android.synthetic.main.fragment_operation.*
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.meewii.mentalarithmetic.OperationListController
import com.meewii.mentalarithmetic.models.Operator
import android.preference.PreferenceManager
import com.meewii.mentalarithmetic.core.App
import com.meewii.mentalarithmetic.ui.activities.SettingsActivity
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.presenters.AdditionsPresenter
import javax.inject.Inject


class AdditionsFragment : Fragment() {

    private var mOperationList: MutableList<Operation>? = null

    @Inject
    lateinit var presenter: AdditionsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        @Suppress("UnnecessaryVariable")
        val view = inflater.inflate(R.layout.fragment_operation, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity.application as App).appComponent()!!.inject(this)

        // create an empty list of operation
        mOperationList = mutableListOf()

        // set up list
        val layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val mainAdapter = MainAdapter(context, mOperationList!!)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.adapter = mainAdapter

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val difficultyStr = preferences.getString(SettingsActivity.PREF_LEVEL_ADDITIONS, Difficulty.EASY.toString())
        val difficulty = Difficulty.valueOf(difficultyStr)

        // create controller
        val operationListController = OperationListController (
                context,
                Operator.ADDITION,
                difficulty,
                mOperationList,
                mRecyclerView,
                mainAdapter,
                solutionInput,
                currentFormula
        )

        // init the calculator
        operationListController.setCalculator()

        // Test presenter
        presenter.init("Hello I'm injected with Dagger")

        // set listener on button
        submitButton.setOnClickListener { operationListController.onSubmitSolution() }
    }
}