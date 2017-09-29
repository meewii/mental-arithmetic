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

    private val TAG: String = "GameViewModel"

    // The operation that the user has to currently solve
    val currentOperation = MutableLiveData<Operation>()
    // the list of past operations
    val operationList = MutableLiveData<ArrayList<Operation>>()

    fun loadOperation(): MutableLiveData<Operation> {
        currentOperation.value = gameRepository.generateOperation()
        return currentOperation
    }

    fun loadOperationList(): MutableLiveData<ArrayList<Operation>> {
        operationList.value = gameRepository.generateOperationList()
        return operationList
    }


    fun submitSolution(submittedSolution: Editable) {
        val userInputStr: String = submittedSolution.toString().trim()
        Log.d(Const.APP_TAG, "[GameViewModel#submitSolution] userInputStr: $userInputStr")

        // TODO: add a state livedata to change state of edittext with observer

        if(currentOperation.value == null) {
            Log.e(Const.APP_TAG, "[GameViewModel#submitSolution] currentOperation.value is null")
            return
        }

        // Parse to integer
        currentOperation.value?.userSolution = Integer.valueOf(userInputStr)

        // check if it's the correct solution
        if (currentOperation.value?.userSolution == currentOperation.value?.solution) {
            currentOperation.value?.status = Status.SUCCESS
//            score.succeededOp += 1
        } else {
            currentOperation.value?.status = Status.FAIL
//            score.failedOp += 1
        }

//        // add submitted answer to the list
//        Log.d(Const.APP_TAG, "[GameViewModel#submitSolution] " +
//                "currentOperation: ${currentOperation.value?.getFullUserOperation()} " +
//                "and status: ${currentOperation.value?.status}")

        val myList: ArrayList<Operation>? = operationList.value
        Log.d(Const.APP_TAG, "[GameViewModel#submitSolution] 1 operationList.value: ${operationList.value?.size}")
        myList?.add(currentOperation.value!!)
        Log.d(Const.APP_TAG, "[GameViewModel#submitSolution] 2 operationList.value: ${operationList.value?.size}")
        operationList.value = myList
        Log.d(Const.APP_TAG, "[GameViewModel#submitSolution] 3 operationList.value: ${operationList.value?.size}")
    }

}