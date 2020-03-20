package com.example.rockpaperscissors.converters

import androidx.room.TypeConverter
import com.example.rockpaperscissors.enums.Move

class MoveConverter  {
  @TypeConverter
  fun moveToInt(value: Move): Int = value.ordinal
  @TypeConverter
  fun intToMove(value: Int) = enumValueOf<Move>(value.toString())
}
