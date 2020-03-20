package com.example.rockpaperscissors.converters

import androidx.room.TypeConverter
import com.example.rockpaperscissors.enums.MatchResult

class MatchResultConverter {
  @TypeConverter
  fun matchResultToInt(value: MatchResult): Int = value.ordinal
  @TypeConverter
  fun intToMatchResult(value: Int) = enumValueOf<MatchResult>(value.toString())

}
