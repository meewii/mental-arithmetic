package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.Editable
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
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
    var currentOperation: MutableLiveData<Operation> = MutableLiveData()
    init {
        currentOperation = loadOperation()
    }

    // Loader
    fun loadOperation(): MutableLiveData<Operation> {
        currentOperation.value = gameRepository.generateOperation()
        return currentOperation
    }

    /**
     * Check submitted solution and update current operation with status SUCCESS or FAILED
     * depending of the solution
     */
    fun submitSolution(submittedSolution: Editable) {
        val userInputStr: String = submittedSolution.toString().trim()
        Log.d(Const.APP_TAG, "[GameViewModel#submitSolution] userInputStr: $userInputStr")

        // TODO: add a state livedata to change state of edittext with observer

        if(currentOperation.value == null) {
            Log.e(Const.APP_TAG, "[GameViewModel#submitSolution] currentOperation.value is null")
            return
        }

        // Copy current operation
        val currentOperationCopy: Operation = currentOperation.value!!
        // Parse submitted solution to integer
        currentOperationCopy.userSolution = Integer.valueOf(userInputStr)

        // check if it's the correct solution
        if (currentOperationCopy.userSolution == currentOperationCopy.solution) {
            currentOperationCopy.status = Status.SUCCESS
//            score.succeededOp += 1
        } else {
            currentOperationCopy.status = Status.FAIL
//            score.failedOp += 1
        }

        currentOperation.value = currentOperationCopy
    }

    /**
     * Add current operation to the list of operation
     */
    fun updateList() {
        gameRepository.addOperationToList(currentOperation.value!!)
        liveOperationList = loadOperationList()
    }

}