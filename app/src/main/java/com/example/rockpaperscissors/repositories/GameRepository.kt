package com.example.rockpaperscissors.repositories

import android.content.Context
import com.example.rockpaperscissors.dao.GameDao
import com.example.rockpaperscissors.database.GameRoomDatabase
import com.example.rockpaperscissors.models.Game

class GameRepository(context: Context) {
  private val gameDao: GameDao

  init {
    val database = GameRoomDatabase.getDatabase(context)
    gameDao = database!!.gameDao()
//    TODO("werk de !! weg met let")
  }

  suspend fun getAllGames(): List<Game> = gameDao.getAllGames()

  suspend fun getWins(): Int = gameDao.getWins()

  suspend fun getLose(): Int = gameDao.getLose()

  suspend fun getDraws(): Int = gameDao.getDraws()

  suspend fun insertGame(game: Game) = gameDao.insertGame(game)

  suspend fun deleteGame(game: Game) = gameDao.deleteGame(game)

  suspend fun deleteAllGamesFromHistory() = gameDao.deleteAllGamesFromHistory()

}
