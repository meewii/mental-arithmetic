package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Status
import com.meewii.mentalarithmetic.ui.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.content_game.*
import javax.inject.Inject

class GameActivity : BaseActivity(R.layout.activity_game) {

    private val gameViewModel by lazy { getViewModel(GameViewModel::class.java) as GameViewModel }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var operationAdapter: PastOperationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up list view with the empty operation list
        setUpAdapter(gameViewModel.liveOperationList)

        observeLiveData()
    }

    override fun onStart() {
        super.onStart()

        // Click listener on the button that submits the user's solution
        submitButton.setOnClickListener {
            gameViewModel.submitSolution(solutionInput.text)
        }
    }


    /**
     * Prepare the RecyclerView to receive the list of Operations
     */
    private fun setUpAdapter(operationList: MutableLiveData<ArrayList<Operation>>) {
        operationAdapter = PastOperationsAdapter(applicationContext, operationList.value)
        recyclerView.adapter = operationAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun observeLiveData() {

        // Observe current Operation
        gameViewModel.currentOperation.observe(this, Observer<Operation> { operation ->
            when (operation?.status) {
                Status.UNCHECKED -> {
                    Log.d(Const.APP_TAG, "[GameActivity#observeLiveData()] UNCHECKED: $operation")
                    currentFormulaView.text = operation.getFormula()
                }
                Status.SUCCESS -> {
                    Log.i(Const.APP_TAG, "[GameActivity#observeLiveData()] SUCCESS: $operation")
                    // add operation to list
                    gameViewModel.updateList()

                    refreshView()
                    // TODO: update score
                }
                Status.FAIL->{
                    Log.e(Const.APP_TAG, "[GameActivity#observeLiveData()] FAIL: $operation")
                    // add operation to list
                    gameViewModel.updateList()

                    refreshView()
                    // TODO: update fail limit
                }
            }
        })

        // Observe the list of Operations
        gameViewModel.liveOperationList.observe(this, Observer<ArrayList<Operation>> { operationList ->
            Log.d(Const.APP_TAG, "[GameActivity#observeLiveData()] liveOperationList: $operationList")
            if(operationList != null) {
                operationAdapter.notifyDataSetChanged()
                val pos: Int = operationList.size
                recyclerView.scrollToPosition(pos - 1)
            }
        })

        // Observe the state of the EditText
        gameViewModel.liveEditTextState.observe(this, Observer<GameViewModel.State> {
            state ->
            when(state) {
                GameViewModel.State.ERROR_EMPTY -> {
                    solutionInput.error = getString(R.string.error_input_required)
                }
                GameViewModel.State.ERROR_NAN -> {
                    solutionInput.error = getString(R.string.error_input_nan)
                }
                else -> {}
            }
        })

    }

    /**
     * Update the list with new data and display a new formula
     */
    private fun refreshView() {
        // generate new operation
        solutionInput.setText("")
        gameViewModel.loadOperation()
    }

}
