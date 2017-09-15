package com.meewii.mentalarithmetic.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.MainAdapter
import com.meewii.mentalarithmetic.R
import kotlinx.android.synthetic.main.fragment_operation.*
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.meewii.mentalarithmetic.core.App
import com.meewii.mentalarithmetic.presenters.AdditionsPresenter
import javax.inject.Inject


class AdditionsFragment : Fragment(), OperationFragment {

    @Inject
    lateinit var presenter: AdditionsPresenter

    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        @Suppress("UnnecessaryVariable")
        val view = inflater.inflate(R.layout.fragment_operation, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity.application as App).appComponent()!!.inject(this)

        // Init presenter
        presenter
                .init("Hello I'm injected with Dagger")
                .attachView(this)
                .generateOperation()

        // set up list
        val layoutManager = LinearLayoutManager(activity.applicationContext, LinearLayout.VERTICAL, false)
        mainAdapter = MainAdapter(activity.applicationContext, presenter.operationList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mainAdapter

        // set listener on button
        submitButton.setOnClickListener {
            presenter.onSubmitSolution(solutionInput.text)
        }
    }


    /**
     * Sets the input to an empty string
     * and generates a new formula
     */
    override fun resetCalculator() {
        solutionInput.setText("")
        presenter.generateOperation()
    }

    /**
     * Display current formula in the view
     */
    override fun displayFormula(formula: String) {
        currentFormulaView.text = formula
    }

    /**
     * Display an error on the input field
     */
    override fun displayError(errMessageId: Int) {
        solutionInput.error = getString(errMessageId)
    }

    /**
     * Update the RecyclerView with new data
     */
    override fun updateList() {
        mainAdapter.notifyDataSetChanged()

        val pos: Int = presenter.operationList.size
        recyclerView.scrollToPosition(pos - 1)

        // reset current operation
        resetCalculator()
    }
}