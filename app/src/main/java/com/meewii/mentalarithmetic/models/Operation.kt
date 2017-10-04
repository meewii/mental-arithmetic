package com.meewii.mentalarithmetic.models

import com.meewii.mentalarithmetic.models.Operator.*

data class Operation(
        private val operator: Operator,
        private val operandA: Int,
        private val operandB: Int,
        var status: Status = Status.UNCHECKED
) {

    var userSolution: Int? = null

    /**
     * Calculates its own solution according of its operands and its operator
     */
    val solution: Int = generateSolution()

    /**
     * Returns a string of the full formula + its solution
     * E.g. "12 + 5 = 17"
     */
    fun getFullOperation(): String = "$operandA ${operator.sign} $operandB = $solution"

    /**
     * Returns a string of the full formula + the user's solution (even if wrong answer)
     * E.g. "12 + 5 = 4512"
     */
    fun getFullUserOperation(): String = "$operandA ${operator.sign} $operandB = $userSolution"

    /**
     * Returns a string of the formula
     * E.g. "12 + 5"
     */
    fun getFormula(): String = "$operandA ${operator.sign} $operandB"

    /**
     * Returns the solution
     */
    private fun generateSolution(): Int = when (this.operator) {
        ADDITION -> this.operandA + this.operandB
        SUBTRACTION -> this.operandA - this.operandB
        MULTIPLICATION -> this.operandA * this.operandB
        DIVISION -> this.operandA / this.operandB
    }

    override fun toString(): String =
            "Operation: ${getFullOperation()} ${getStatusString()}"

    private fun getStatusString(): String =
        when(status) {
            Status.UNCHECKED -> "- $status"
            else -> "- user solution: $userSolution - $status"
        }

}

enum class Operator(val sign: String, val displayName: String) {
    ADDITION("+", "Addition"),
    SUBTRACTION("-", "Subtraction"),
    MULTIPLICATION("*", "Multiplication"),
    DIVISION("/", "Division")
}

enum class Status {
    UNCHECKED,
    SUCCESS,
    FAIL
}

enum class Difficulty(val displayName: String) {
    VERY_EASY("Very easy"),
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    VERY_HARD("Very hard")
}