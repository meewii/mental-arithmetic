package com.meewii.mentalarithmetic.models

data class Operation (
        val operator: Operator,
        val operandA: Int,
        val operandB: Int,
        var status: Status = Status.UNCHECKED
) {

    var userSolution: Int? = null
    val solution: Int = initSolution()

    /**
     * Calculates its own solution according of its operands and its operator
     */
    private fun initSolution(): Int {
        when(this.operator) {
            Operator.ADDITION -> return this.operandA + this.operandB
            Operator.SUBTRACTION -> return this.operandA - this.operandB
            Operator.MULTIPLICATION -> return this.operandA * this.operandB
            Operator.DIVISION -> return this.operandA / this.operandB
        }
    }

    /**
     * Returns a string of the full formula + its solution
     * E.g. "12 + 5 = 17"
     */
    fun getFullOperation(): String {
        val operatorSign: String = operator.sign
        return "$operandA $operatorSign $operandB = $solution"
    }

    /**
     * Returns a string of the full formula + the user's solution
     * E.g. "12 + 5 = 125"
     */
    fun getFullUserOperation(): String {
        val operatorSign: String = operator.sign
        return "$operandA $operatorSign $operandB = $userSolution"
    }

    /**
     * Returns a string of the formula
     * E.g. "12 + 5"
     */
    fun getFormula(): String {
        val operatorSign: String = operator.sign
        return "$operandA $operatorSign $operandB"
    }

}

enum class Operator(val sign: String) {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/")
}

enum class Status {
    UNCHECKED,
    SUCCESS,
    FAIL
}

