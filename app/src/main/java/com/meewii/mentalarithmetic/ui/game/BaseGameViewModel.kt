package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import android.text.Editable
import android.util.Log
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.models.Status

abstract class BaseGameViewModel constructor(
        private val gameRepository: GameRepository,
        sharedPreferences: SharedPreferences): ViewModel() {


    val operator: Operator
    val difficulty: Difficulty
    init {
        val op = sharedPreferences.getString(Const.OPERATOR_TYPE_EXTRA, Operator.ADDITION.toString())
        val di = sharedPreferences.getString(Const.DIFFICULTY_EXTRA, Difficulty.EASY.toString())
        operator = Operator.valueOf(op)
        difficulty = Difficulty.valueOf(di)
    }


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
        liveCurrentOperation.value = gameRepository.generateOperation(operator, difficulty)
        Log.v(Const.APP_TAG, "[GameViewModel#loadOperation] ${liveCurrentOperation.value}")
        return liveCurrentOperation
    }


    // the State of EditText
    var liveEditTextState: MutableLiveData<BaseGameViewModel.EditTextState> = MutableLiveData()
    init {
        liveEditTextState.value = BaseGameViewModel.EditTextState.PRISTINE
    }
    enum class EditTextState {
        PRISTINE, ERROR_EMPTY, ERROR_NAN
    }


    abstract fun submitSolution(submittedSolution: Editable)


    /**
     * Reset all live data
     */
    open fun resetAllLiveData() {
        liveOperationList.value = gameRepository.newOperationList()
        liveCurrentOperation.value = gameRepository.generateOperation(operator, difficulty)
        liveEditTextState.value = EditTextState.PRISTINE
    }

    /**
     * Add current operation to the list of operation
     */
    fun updateList() {
        gameRepository.addOperationToList(liveCurrentOperation.value!!)
        liveOperationList = loadOperationList()
    }

    /**
     * Checks if the submitted solution is the correct one
     */
    protected fun validateSubmittedSolution(currentOperation: Operation): Status =
            if (currentOperation.userSolution == currentOperation.solution) {
                Status.SUCCESS
            } else {
                Status.FAIL
            }

}