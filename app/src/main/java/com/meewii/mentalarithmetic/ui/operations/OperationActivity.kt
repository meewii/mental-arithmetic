package com.meewii.mentalarithmetic.ui.operations

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_operation.*
import kotlinx.android.synthetic.main.content_operation.*
import javax.inject.Inject

class OperationActivity : BaseActivity() {

    @Inject
    lateinit var presenter: OperationPresenter
    @Inject
    lateinit var layoutManager: LinearLayoutManager
    @Inject
    lateinit var operationAdapter: OperationAdapter

    lateinit var difficulty: Difficulty
    lateinit var operator: Operator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activityComponent.inject(this)

        val operator = intent.extras.getString(Const.OPERATOR_TYPE_EXTRA)
        val difficulty = intent.extras.getString(Const.DIFFICULTY_EXTRA)
        this.operator = Operator.valueOf(operator)
        this.difficulty = Difficulty.valueOf(difficulty)
        Log.i("MainActivity", "Operator: $operator - Difficulty: $difficulty")


        // Init presenter
        presenter
                .init(this.difficulty, this.operator)
                .attachView(this)
                .newGame()

        // set up list
        setUpAdapter(presenter.operationList)

        // set listener on button
        submitButton.setOnClickListener {
            presenter.onSubmitSolution(solutionInput.text)
        }
    }


    /**
     * Display current formula in the view
     */
    private fun setUpAdapter(operationList: MutableList<Operation>) {
        recyclerView.adapter = operationAdapter.addOperations(operationList)
        recyclerView.layoutManager = layoutManager
    }

    fun showSoftKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        solutionInput.requestFocus()
        inputMethodManager.showSoftInput(solutionInput, 0)
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
    fun newOperation() {
        solutionInput.setText("")
        inputContainer.visibility = View.VISIBLE
        presenter.generateOperation()
    }

    /**
     * Display information to end the game
     */
    fun disableGame() {
        inputContainer.visibility = View.INVISIBLE
    }

    /**
     * Update the RecyclerView with new data
     */
    fun updateList() {
        operationAdapter.notifyDataSetChanged()

        val pos: Int = presenter.operationList.size
        recyclerView.scrollToPosition(pos - 1)
    }

}