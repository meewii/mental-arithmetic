package com.meewii.mentalarithmetic.ui.game

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Status
import com.meewii.mentalarithmetic.ui.BaseActivity
import com.meewii.mentalarithmetic.ui.nav.HomeNavActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.content_game.*


class GameActivity : BaseActivity(R.layout.activity_game) {

    private val registry = LifecycleRegistry(this)
    override fun getLifecycle(): LifecycleRegistry = registry

    private val gameViewModel by lazy { getViewModel(GameViewModel::class.java) as GameViewModel }

    private lateinit var operationAdapter: PastOperationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up list view with the empty operation list
        setUpAdapter(gameViewModel.liveOperationList)

        showSoftKeyboard()
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
        gameViewModel.liveCurrentOperation.observe(this, Observer<Operation> { operation ->
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
                }
                Status.FAIL -> {
                    Log.e(Const.APP_TAG, "[GameActivity#observeLiveData()] FAIL: $operation")
                    // add operation to list
                    gameViewModel.updateList()

                    refreshView()
                }
            }
        })

        // Observe the list of Operations
        gameViewModel.liveOperationList.observe(this, Observer<ArrayList<Operation>> { operationList ->
            if (operationList != null) refreshList()
        })

        // Observe the state of the EditText
        gameViewModel.liveEditTextState.observe(this, Observer<GameViewModel.EditTextState> { state ->
            when (state) {
                GameViewModel.EditTextState.ERROR_EMPTY -> {
                    solutionInput.error = getString(R.string.error_input_required)
                }
                GameViewModel.EditTextState.ERROR_NAN -> {
                    solutionInput.error = getString(R.string.error_input_nan)
                }
                else -> {
                }
            }
        })

        // Observe the state of the Game
        gameViewModel.liveGameState.observe(this, Observer<GameViewModel.GameState> { state ->
            when (state) {
                GameViewModel.GameState.OVER -> {
                    hideSoftKeyboard()
                    inputContainer.visibility = View.INVISIBLE
                    val gameOverBar = Snackbar
                            .make(container, "Game over! Points: ${gameViewModel.liveScore.value?.points}", Snackbar.LENGTH_INDEFINITE)
                            .setAction("New game?") {
                                gameViewModel.newGame()
                            }
                    gameOverBar.show()
                }
                GameViewModel.GameState.NEW -> {
                    inputContainer.visibility = View.VISIBLE
                    showSoftKeyboard()
                }
                else -> {
                }
            }
        })

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
        gameViewModel.loadOperation()
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    private fun showSoftKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        solutionInput.requestFocus()
        inputMethodManager.showSoftInput(solutionInput, 0)
    }

    /**
     * On back pressed, we check if the game is over, prompt the user if they really want to quit
     * the game and save its score if he wants to quit
     */
    override fun onBackPressed() {
        if(gameViewModel.isGameOver()) {
            gameViewModel.clearGame()
            goToMainPage()
            return
        }

        val dialogBuilder = AlertDialog.Builder(this@GameActivity)
        dialogBuilder.setMessage(R.string.prompt_quit_game)
                .setPositiveButton(R.string.yes, { dialog, id ->
                    gameViewModel.saveCurrentScore()
                    gameViewModel.clearGame()
                    dialog.dismiss()
                    goToMainPage()
                })
                .setNegativeButton(R.string.cancel, { dialog, id ->
                    dialog.dismiss()
                })
        dialogBuilder.create().show()
    }

    private fun goToMainPage() {
        val intent = Intent(this@GameActivity, HomeNavActivity::class.java)
        // The keyword "or" is a bit confusing but all flags will be used
        intent.flags = FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
