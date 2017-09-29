package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.Editable
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Operation
import javax.inject.Inject

class GameViewModel @Inject constructor(
        private val gameRepository: GameRepository,
        private val gameController: GameController) : ViewModel() {

    private val TAG: String = "GameViewModel"

    // The operation that the user has to currently solve
    val currentOperation = MutableLiveData<Operation>()
    // the list of past operations
    val operationList = MutableLiveData<List<Operation>>()

    fun getOperation(): MutableLiveData<Operation> {
        currentOperation.value = gameController.generateOperation()
        return currentOperation
    }

    fun submitSolution(submittedSolution: Editable) {
        val userInputStr: String = submittedSolution.toString().trim()
        Log.d(Const.APP_TAG, "[$TAG#onSubmitSolution] userInputStr: $userInputStr")
    }

}