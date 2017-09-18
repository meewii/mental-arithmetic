package com.meewii.mentalarithmetic.presenters

import android.text.Editable
import com.meewii.mentalarithmetic.ui.fragments.BaseFragment

interface OperationPresenter {
    // init methods
    fun init(saySomething: String): OperationPresenter
    fun attachView(fragment: BaseFragment): OperationPresenter
    fun generateOperation(): OperationPresenter

    fun onSubmitSolution(submittedSolution: Editable)
}
