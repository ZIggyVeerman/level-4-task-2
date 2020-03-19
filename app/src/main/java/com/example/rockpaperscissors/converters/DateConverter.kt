package com.example.rockpaperscissors.converters

import androidx.room.TypeConverter
import java.sql.Date

class DateConverter {
  @TypeConverter
  fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
  }

  @TypeConverter
  fun dateToTimestamp(date: Date?): Long? {
    return date?.time
  }
}
