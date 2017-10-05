package com.meewii.mentalarithmetic.data.database

import android.arch.persistence.room.TypeConverter
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operator

/**
 * Helps Room convert the Enums Difficulty and Operator to/from String
 */
class ScoreConverters {

    @TypeConverter
    fun stringToDifficulty(value: String?): Difficulty? = if (value == null) null else Difficulty.valueOf(value)

    @TypeConverter
    fun difficultyToString(difficulty: Difficulty?): String? = difficulty?.name

    @TypeConverter
    fun stringToOperator(value: String?): Operator? = if (value == null) null else Operator.valueOf(value)

    @TypeConverter
    fun operatorToString(operator: Operator?): String? = operator?.name

}