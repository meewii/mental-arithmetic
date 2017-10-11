package com.meewii.mentalarithmetic.modules.game.training

import android.content.SharedPreferences
import android.text.Editable
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.base.BaseGameViewModel
import com.meewii.mentalarithmetic.common.GameRepository
import javax.inject.Inject

class TrainingGameViewModel @Inject constructor(
        gameRepository: GameRepository,
        sharedPreferences: SharedPreferences) : BaseGameViewModel(gameRepository, sharedPreferences) {

    /**
     * Check submitted solution and update current operation with status SUCCESS or FAILED
     * depending of the solution.
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

        // Parse submitted solution to integer
        try {
            currentOperation.userSolution = Integer.valueOf(userInputStr)
        } catch (e: NumberFormatException) {
            liveEditTextState.value = EditTextState.ERROR_NAN
            return
        }

        currentOperation.status = validateSubmittedSolution(currentOperation)

        liveCurrentOperation.value = currentOperation
    }
}