package com.example.rockpaperscissors.database

import android.content.Context
import androidx.room.*
import com.example.rockpaperscissors.converters.DateConverter
import com.example.rockpaperscissors.converters.MatchResultConverter
import com.example.rockpaperscissors.converters.MoveConverter
import com.example.rockpaperscissors.dao.GameDao
import com.example.rockpaperscissors.models.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, MoveConverter::class, MatchResultConverter::class)
abstract class GameRoomDatabase : RoomDatabase() {

  abstract fun gameDao(): GameDao

  companion object {
    private const val DATABASE_NAME = "RPS_GAME_DATABASE"

    @Volatile
    private var gameRoomDatabaseInstance: GameRoomDatabase? = null

    fun getDatabase(context: Context): GameRoomDatabase? {
      if (gameRoomDatabaseInstance != null) return gameRoomDatabaseInstance

      synchronized(GameRoomDatabase::class.java) {
        if (gameRoomDatabaseInstance == null) {
          gameRoomDatabaseInstance = Room.databaseBuilder(
              context.applicationContext,
              GameRoomDatabase::class.java, DATABASE_NAME
            )
            .build()
        }
      }
      return gameRoomDatabaseInstance
    }
  }
}
