package com.meewii.mentalarithmetic.ui.fragments

interface OperationFragment {
    fun displayFormula(formula: String) {}
    fun displayError(errMessageId: Int) {}
    fun resetCalculator()
    fun updateList()
}