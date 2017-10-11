package com.meewii.mentalarithmetic.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Status
import com.meewii.mentalarithmetic.modules.game.PastOperationsAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.content_game.*
import java.util.*
import java.util.concurrent.TimeUnit


abstract class BaseGameActivity : BaseActivity(R.layout.activity_game) {

    private lateinit var operationAdapter: PastOperationsAdapter
    private val timerHandler: Handler = Handler()
    private var timerRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        showSoftKeyboard()
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }


    /**
     * Observe current Operation
     */
    protected fun observeLiveCurrentOperation(baseGameViewModel: BaseGameViewModel) {
        baseGameViewModel.liveCurrentOperation.observe(this, Observer<Operation> { operation ->
            when (operation?.status) {
                Status.UNCHECKED -> {
                    Log.d(Const.APP_TAG, "[GameActivity#observeLiveData()] $operation")
                    currentFormulaView.text = operation.getFormula()
                }
                Status.SUCCESS -> {
                    Log.i(Const.APP_TAG, "[GameActivity#observeLiveData()] $operation")
                    // add operation to list
                    baseGameViewModel.updateList()
                    baseGameViewModel.loadOperation()

                    refreshView()
                }
                Status.FAIL -> {
                    Log.e(Const.APP_TAG, "[GameActivity#observeLiveData()] $operation")
                    // add operation to list
                    baseGameViewModel.updateList()
                    baseGameViewModel.loadOperation()

                    refreshView()
                }
            }
        })
    }

    /**
     * Observe the list of Operations
     */
    protected fun observeLiveOperationList(baseGameViewModel: BaseGameViewModel) {
        baseGameViewModel.liveOperationList.observe(this, Observer<ArrayList<Operation>> { operationList ->
            if (operationList != null) refreshList()
        })
    }

    /**
     * Observe the state of the EditText
     */
    protected fun observeLiveEditTextState(baseGameViewModel: BaseGameViewModel) {
        baseGameViewModel.liveEditTextState.observe(this, Observer<BaseGameViewModel.EditTextState> { state ->
            when (state) {
                BaseGameViewModel.EditTextState.ERROR_EMPTY -> {
                    solutionInput.error = getString(R.string.error_input_required)
                }
                BaseGameViewModel.EditTextState.ERROR_NAN -> {
                    solutionInput.error = getString(R.string.error_input_nan)
                }
                else -> {
                }
            }
        })
    }

    /**
     * Observe the duration of the game (is triggered every second)
     */
    protected fun observeLiveGameDuration(baseGameViewModel: BaseGameViewModel) {
        baseGameViewModel.liveGameDuration.observe(this, Observer<Long> { duration ->
            if(duration == null) {
                timeView.text = getGameDurationString()
            } else {
                timeView.text = getGameDurationString(duration)
            }
        })
    }



    /**
     * Prepare the RecyclerView to receive the list of Operations and the button click listener
     */
    protected fun setUpView(baseGameViewModel: BaseGameViewModel) {
        // Toolbar views
        titleView.text = "${baseGameViewModel.operator.displayName} - ${baseGameViewModel.difficulty.displayName}"
        timeView.text = getGameDurationString()

        // Click listener on the button that submits the user's solution
        submitButton.setOnClickListener {
            baseGameViewModel.submitSolution(solutionInput.text)
        }

        val operationList: MutableLiveData<ArrayList<Operation>> = baseGameViewModel.liveOperationList
        operationAdapter = PastOperationsAdapter(applicationContext, operationList.value)
        recyclerView.adapter = operationAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }


    /**
     * Start the timer that'll count the game duration
     */
    protected fun startTimer(baseGameViewModel: BaseGameViewModel) {
        if(timerRunnable == null) {
            timerRunnable = object : Runnable {
                override fun run() {
                    baseGameViewModel.loadGameDuration()
                    timerHandler.postDelayed(this, 1000)
                }
            }
        }
        timerHandler.postDelayed(timerRunnable, 1000)
    }

    /**
     * Stop the timer that counts the game duration
     */
    protected fun stopTimer() {
        timerHandler.removeCallbacksAndMessages(null)
    }

    /**
     * Utility method to convert a timestamp into a "HH:mm:ss" string
     */
    private fun getGameDurationString(duration: Long = 0): String {
        return String.format(
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(duration) % TimeUnit.MINUTES.toSeconds(1)
        )
    }

    /**
     * Update the list with new data
     */
    private fun refreshList() {
        operationAdapter.notifyDataSetChanged()
        val pos: Int = operationAdapter.itemCount
        recyclerView.scrollToPosition(pos - 1)
    }

    /**
     * Generate new operation and empty the input field
     */
    private fun refreshView() {
        solutionInput.setText("")
    }
}
