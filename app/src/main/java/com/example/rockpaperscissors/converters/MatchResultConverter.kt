package com.example.rockpaperscissors.converters

import androidx.room.TypeConverter
import com.example.rockpaperscissors.enums.MatchResult

class MatchResultConverter {
  @TypeConverter
  fun matchResultToInt(value: MatchResult) = value to Int
  @TypeConverter
  fun intToMatchResult(value: Int) = enumValueOf<MatchResult>(value.toString())

}
