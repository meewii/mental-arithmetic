package com.meewii.mentalarithmetic.models

data class Operation (
        val operator: Operator = Operator.ADDITION,
        val operandA: Int = 0,
        val operandB: Int = 0,
        var status: Status = Status.UNCHECKED
) {

    var solution: Int = 0
    init {
        when(this.operator) {
            Operator.ADDITION -> this.solution = this.operandA + this.operandB
            Operator.SUBTRACTION -> this.solution = this.operandA - this.operandB
            Operator.MULTIPLICATION -> this.solution = this.operandA * this.operandB
            Operator.DIVISION -> this.solution = this.operandA / this.operandB
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

