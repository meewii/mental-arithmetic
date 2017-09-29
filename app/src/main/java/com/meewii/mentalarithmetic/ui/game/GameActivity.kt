package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
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

        // Dummy
        val editor = sharedPreferences.edit()
        editor.putString(Const.DIFFICULTY_EXTRA, Difficulty.HARD.toString())
        editor.putString(Const.OPERATOR_TYPE_EXTRA, Operator.SUBTRACTION.toString())
        editor.apply()

        // Observe current Operation
        gameViewModel.loadOperation().observe(this, Observer<Operation> { operation ->
            when (operation?.status) {
                Status.UNCHECKED -> {
                    Log.d(Const.APP_TAG, "[GameActivity#loadOperation().observe] UNCHECKED: $operation")
                    currentFormulaView.text = operation.getFormula()
                }
                Status.SUCCESS -> {
                    // TODO: 1- add operation to list
                    // 2- generate new operation
                    // 3- update score
//                    Log.d(Const.APP_TAG, "[GameActivity#loadOperation().observe] SUCCESS: $operation")
                }
                Status.FAIL->{
                    // TODO: 1- add operation to list
                    // 2- generate new operation
                    // 3- update fail limit
//                    Log.d(Const.APP_TAG, "[GameActivity#loadOperation().observe] FAIL: $operation")
                }
            }
        })

        // Observe the list of Operations
        gameViewModel.loadOperationList().observe(this, Observer<ArrayList<Operation>> { operationList ->
            Log.w(Const.APP_TAG, "[GameActivity#loadOperationList().observe] : $operationList")
            if(operationList != null) {
                operationAdapter.updateData(operationList)
            }
        })

        // Set up list view with the empty operation list
        setUpAdapter(gameViewModel.operationList)

    }

    override fun onStart() {
        super.onStart()
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

}
