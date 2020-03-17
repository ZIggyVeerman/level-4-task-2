package com.example.rockpaperscissors.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rockpaperscissors.enums.Move
import com.example.rockpaperscissors.enums.MatchResult

import kotlinx.android.parcel.Parcelize
import java.sql.Date

@Parcelize
@Entity(tableName = "game_table")
data class Game (
  @ColumnInfo(name = "date")
  var date: Date?,

  @ColumnInfo(name = "playerMove")
  var playerMove: Move,

  @ColumnInfo(name = "botMove")
  var botMove : Move,

  @ColumnInfo(name = "matchResult")
  var matchResult: MatchResult? = null,

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  var id: Long? = null
) : Parcelable
