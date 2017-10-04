package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.Editable
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Status
import javax.inject.Inject

class GameViewModel @Inject constructor(
        private val gameRepository: GameRepository) : ViewModel() {

    // the list of past operations
    var liveOperationList: MutableLiveData<ArrayList<Operation>> = MutableLiveData()
    init {
        liveOperationList = loadOperationList()
    }
    // Loader
    private fun loadOperationList(): MutableLiveData<ArrayList<Operation>> {
        liveOperationList.value = gameRepository.getOperationList()
        Log.d(Const.APP_TAG, "[GameViewModel#loadOperationList] liveOperationList.value: ${liveOperationList.value}")
        return liveOperationList
    }



    // The operation that the user has to currently solve
    var liveCurrentOperation: MutableLiveData<Operation> = MutableLiveData()
    init {
        liveCurrentOperation = loadOperation()
    }
    // Loader
    fun loadOperation(): MutableLiveData<Operation> {
        liveCurrentOperation.value = gameRepository.generateOperation()
        return liveCurrentOperation
    }



    // the State of EditText
    var liveEditTextState: MutableLiveData<EditTextState> = MutableLiveData()
    init {
        liveEditTextState.value = EditTextState.PRISTINE
    }


    // the State of the game
    var liveGameState: MutableLiveData<GameState> = MutableLiveData()
    init {
        liveGameState.value = GameState.NEW
    }


    private val FAIL_LIMIT: Int = 5
    // the Score of the User
    var liveScore: MutableLiveData<ScoreEntry> = MutableLiveData()
    init {
        liveScore.value = gameRepository.generateScore()
    }




    /**
     * Check submitted solution and update current operation with status SUCCESS or FAILED
     * depending of the solution. Update Score according to success.
     */
    fun submitSolution(submittedSolution: Editable) {
        val userInputStr: String = submittedSolution.toString().trim()

        // Check if the submitted solution is not an empty string
        if(userInputStr.isEmpty()) {
            liveEditTextState.value = EditTextState.ERROR_EMPTY
            return
        }

        // Stop process if current operation is null
        if(liveCurrentOperation.value == null) {
            Log.e(Const.APP_TAG, "[GameViewModel#submitSolution] currentOperation.value is null")
            return
        }

        // Copy current operation and score
        val currentOperation: Operation = liveCurrentOperation.value!!
        var currentScore: ScoreEntry = liveScore.value!!

        // Parse submitted solution to integer
        try {
            currentOperation.userSolution = Integer.valueOf(userInputStr)
        } catch (e: NumberFormatException) {
            liveEditTextState.value = EditTextState.ERROR_NAN
            return
        }

        currentOperation.status = validateSubmittedSolution(currentOperation)

        currentScore = updateScore(currentOperation, currentScore)

        if(isGameOver(currentScore)) {
            // save the duration
            currentScore.updated_at = System.currentTimeMillis()
            gameRepository.saveScore(currentScore)

            liveGameState.value = GameState.OVER
        } else {
            liveGameState.value = GameState.ONGOING
        }

        // trigger observers
        liveScore.value = currentScore
        liveCurrentOperation.value = currentOperation
    }

    /**
     * Checks if the submitted solution is the correct one
     */
    private fun validateSubmittedSolution(currentOperation: Operation): Status {
        return if (currentOperation.userSolution == currentOperation.solution) {
            Status.SUCCESS
        } else {
            Status.FAIL
        }
    }

    /**
     * Increment score's succeeded and failed operation according to Status
     */
    private fun updateScore(operation: Operation, currentScore: ScoreEntry): ScoreEntry {
        if (operation.status == Status.SUCCESS) {
            currentScore.succeededOp += 1
        } else {
            currentScore.failedOp += 1
        }
        return currentScore
    }

    private fun isGameOver(currentScore: ScoreEntry): Boolean =
            currentScore.failedOp >= FAIL_LIMIT

    /**
     * Reset all live data
     */
    fun newGame() {
        liveGameState.value = GameState.NEW
        liveOperationList.value = gameRepository.newOperationList()
        liveScore.value = gameRepository.generateScore()
        liveCurrentOperation.value = gameRepository.generateOperation()
        liveEditTextState.value = EditTextState.PRISTINE
    }

    /**
     * Add current operation to the list of operation
     */
    fun updateList() {
        gameRepository.addOperationToList(liveCurrentOperation.value!!)
        liveOperationList = loadOperationList()
    }

    enum class EditTextState {
        PRISTINE, ERROR_EMPTY, ERROR_NAN
    }

    enum class GameState {
        NEW, OVER, ONGOING
    }

}