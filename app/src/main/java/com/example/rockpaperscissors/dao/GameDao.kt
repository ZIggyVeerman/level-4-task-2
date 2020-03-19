package com.example.rockpaperscissors.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rockpaperscissors.models.Game

@Dao
interface GameDao {
  @Query("SELECT * FROM game_table")
  suspend fun getAllGames(): List<Game>

  @Query("SELECT count(*) FROM game_table WHERE matchResult = 0")
  suspend fun getWins(): Int

  @Query("SELECT count(*) FROM game_table WHERE matchResult = 1")
  suspend fun getLose(): Int

  @Query("SELECT count(*) FROM game_table WHERE matchResult = 2")
  suspend fun getDraws(): Int

  @Insert
  suspend fun insertGame(game: Game)

  @Delete
  suspend fun deleteGame(game: Game)

  @Query("DELETE FROM game_table")
  suspend fun deleteAllGamesFromHistory()
}
