package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.text.Editable
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Status
import javax.inject.Inject

class ScoredGameViewModel @Inject constructor(
        private val gameRepository: GameRepository,
        sharedPreferences: SharedPreferences) : BaseGameViewModel(gameRepository, sharedPreferences) {


    // the State of the game
    var liveGameState: MutableLiveData<GameState> = MutableLiveData()
    init {
        liveGameState.value = GameState.NEW
    }


    companion object {
        // the game will stop after this number of fails
        const val FAIL_LIMIT: Int = 5
    }
    // the Score of the User
    var liveScore: MutableLiveData<ScoreEntry> = MutableLiveData()
    init {
        liveScore.value = gameRepository.generateScore(operator, difficulty)
    }



    /**
     * Check submitted solution and update current operation with status SUCCESS or FAILED
     * depending of the solution. Update Score according to success.
     */
    override fun submitSolution(submittedSolution: Editable) {
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

        if(isFailLimitReached(currentScore)) {
            saveCurrentScore(currentScore)

            liveGameState.value = GameState.OVER
        } else {
            liveGameState.value = GameState.ONGOING
        }

        // trigger observers
        liveScore.value = currentScore
        liveCurrentOperation.value = currentOperation
    }

    /**
     * Save the score in database
     */
    fun saveCurrentScore(currentScore: ScoreEntry = liveScore.value!!) {
        currentScore.updated_at = System.currentTimeMillis()
        gameRepository.saveScore(currentScore)
    }

    /**
     * Reset everything
     */
    fun clearGame() {
        liveGameState.value = GameState.NEW
        liveScore.value = gameRepository.generateScore(operator, difficulty)
        resetAllLiveData()
    }

    /**
     * Save the score in database
     */
    fun isGameOver(): Boolean = liveGameState.value == GameState.OVER


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

    private fun isFailLimitReached(currentScore: ScoreEntry): Boolean =
            currentScore.failedOp >= FAIL_LIMIT


    enum class GameState {
        NEW, OVER, ONGOING
    }
}