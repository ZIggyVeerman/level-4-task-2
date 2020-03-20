package com.example.rockpaperscissors.converters

import androidx.room.TypeConverter
import com.example.rockpaperscissors.enums.Move

class MoveConverter {
  @TypeConverter
  fun matchResultToInt(value: Move): Int = value.ordinal
  @TypeConverter
  fun intToMatchResult(value: Int) = enumValueOf<Move>(value.toString())

}
