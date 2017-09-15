package com.meewii.mentalarithmetic.presenters

import android.text.Editable
import com.meewii.mentalarithmetic.ui.fragments.OperationFragment

interface OperationPresenter {
    // init methods
    fun init(saySomething: String): OperationPresenter
    fun attachView(fragment: OperationFragment): OperationPresenter
    fun generateOperation(): OperationPresenter

    fun onSubmitSolution(submittedSolution: Editable)
}
