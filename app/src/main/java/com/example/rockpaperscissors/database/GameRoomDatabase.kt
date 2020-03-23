package com.example.rockpaperscissors.database

import android.content.Context
import androidx.room.*
import com.example.rockpaperscissors.converters.DateConverter
import com.example.rockpaperscissors.converters.MatchResultConverter
import com.example.rockpaperscissors.converters.MoveConverter
import com.example.rockpaperscissors.dao.GameDao
import com.example.rockpaperscissors.models.Game

// define that this is room database with entities
@Database(entities = [Game::class], version = 1, exportSchema = false)
// define the type converters for this class
@TypeConverters(DateConverter::class, MoveConverter::class, MatchResultConverter::class)
/**
 * An abstract method for getting the implementation room
 */
abstract class GameRoomDatabase : RoomDatabase() {
  // make sure the reminder DAO is added
  abstract fun gameDao(): GameDao
  // to make the database static everything is wrapped in a companion object
  // define the database name
  companion object {
    private const val DATABASE_NAME = "RPS_GAME_DATABASE"
    // we created the room database here
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
