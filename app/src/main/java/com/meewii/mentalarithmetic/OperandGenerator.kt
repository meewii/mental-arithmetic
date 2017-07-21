package com.meewii.mentalarithmetic

import android.util.Log
import com.meewii.mentalarithmetic.models.Operator
import java.util.*

/*
Utility class to generate operands according to Operator and Level of difficulty
*/
class OperandGenerator {
    companion object {

        private val TAG: String = "OperandGenerator"

        fun getOperands(operator: Operator): IntArray {
            val operandA: Int = getRandomPositiveInt(1, 12)
            val operandB: Int = getRandomPositiveInt(1, 12)

            when (operator) {
                Operator.ADDITION -> return intArrayOf(operandA, operandB)
                Operator.MULTIPLICATION -> return intArrayOf(operandA, operandB)
                Operator.SUBTRACTION -> return getOperandsForSubtraction()
                Operator.DIVISION -> return getOperandsForDivision()
            }
        }

        fun getOperandsForSubtraction(): IntArray {
            val solution: Int = getRandomPositiveInt(2, 12)
            var operandB: Int = getRandomPositiveInt(1, 12)

            // we don't want the solution to be negative
            while (solution < operandB) {
                Log.w(TAG, "[getOperandsForSubtraction] retrying for: solution: $solution - operandB: $operandB")
                operandB = getRandomPositiveInt(1, 12)
            }

            val operandA = solution + operandB
            Log.i(TAG, "[getOperandsForSubtraction] solution: $solution - operandA: $operandA | operandB: $operandB")
            return intArrayOf(operandA, operandB)
        }

        fun getOperandsForDivision(): IntArray {
            val solution: Int = getRandomPositiveInt(1, 12)
            val operandB: Int = getRandomPositiveInt(1, 12)

            val operandA = solution * operandB

            Log.i(TAG, "[getOperandsForDivision] solution: $solution - operandA: $operandA | operandB: $operandB")
            return intArrayOf(operandA, operandB)
        }

        /**
         * Get random number between min (incl.) and max (incl.)
         * and different from 1 and 10 (too easy)
         */
        fun getRandomPositiveInt(min: Int, max: Int): Int {
            var num: Int = 1
            while (num == 1 || num == 10) {
                num = Random().nextInt(max) + min
            }
            return num
        }

    }
}