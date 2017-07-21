package com.meewii.mentalarithmetic.utils

import android.util.Log
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operator
import java.util.*

/*
Utility class to generate operands according to Operator and Level of difficulty
*/
class OperandGenerator {
    companion object {

        private val TAG: String = "OperandGenerator"

        fun getOperands(operator: Operator, difficulty: Difficulty = Difficulty.EASY): IntArray {
            val operandA: Int = getRandomPositiveInt(1, getMaxPositiveInt(difficulty))
            val operandB: Int = getRandomPositiveInt(1, getMaxPositiveInt(difficulty))

            when (operator) {
                Operator.ADDITION -> return intArrayOf(operandA, operandB)
                Operator.MULTIPLICATION -> return intArrayOf(operandA, operandB)
                Operator.SUBTRACTION -> return getOperandsForSubtraction(difficulty)
                Operator.DIVISION -> return getOperandsForDivision(difficulty)
            }
        }

        private fun getOperandsForSubtraction(difficulty: Difficulty): IntArray {
            val solution: Int = getRandomPositiveInt(2, getMaxPositiveInt(difficulty))
            var operandB: Int = getRandomPositiveInt(1, getMaxPositiveInt(difficulty))

            // we don't want the solution to be negative
            while (solution < operandB) {
                Log.w(TAG, "[getOperandsForSubtraction] retrying for: solution: $solution - operandB: $operandB")
                operandB = getRandomPositiveInt(1, getMaxPositiveInt(difficulty))
            }

            val operandA = solution + operandB
            Log.i(TAG, "[getOperandsForSubtraction] solution: $solution - operandA: $operandA | operandB: $operandB")
            return intArrayOf(operandA, operandB)
        }

        private fun getOperandsForDivision(difficulty: Difficulty): IntArray {
            val solution: Int = getRandomPositiveInt(1, getMaxPositiveInt(difficulty))
            val operandB: Int = getRandomPositiveInt(1, getMaxPositiveInt(difficulty))

            val operandA = solution * operandB

            Log.i(TAG, "[getOperandsForDivision] solution: $solution - operandA: $operandA | operandB: $operandB")
            return intArrayOf(operandA, operandB)
        }

        /**
         * Get random number between min (incl.) and max (incl.)
         * and different from 1 and 10 (too easy)
         */
        private fun getRandomPositiveInt(min: Int, max: Int): Int {
            var num: Int = 1
            while (num == 1 || num == 10) {
                num = Random().nextInt(max) + min
            }
            return num
        }

        /**
         * Get random number between min (incl.) and max (incl.)
         * and different from 1 and 10 (too easy)
         */
        private fun getMaxPositiveInt(difficulty: Difficulty): Int {
            when(difficulty) {
                Difficulty.VERY_EASY -> return 6
                Difficulty.EASY -> return 12
                Difficulty.MEDIUM -> return 20
                Difficulty.HARD -> return 100
                Difficulty.VERY_HARD -> return 1000
            }
        }

    }
}