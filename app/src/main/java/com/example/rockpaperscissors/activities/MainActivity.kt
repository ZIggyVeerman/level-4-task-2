package com.example.rockpaperscissors.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.enums.Move
import com.example.rockpaperscissors.enums.MatchResult
import com.example.rockpaperscissors.models.Game
import com.example.rockpaperscissors.repositories.GameRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
  private lateinit var gameRepository: GameRepository
  private val mainScope = CoroutineScope(Dispatchers.Main)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbarMain)
    // initialize the game repository give it the context of the activity
    gameRepository = GameRepository(this)

    initViews()
  }

  /**
   * Initviews method to create all things that have to start on create
   */
  private fun initViews() {
    // update stats on page load so that they are on par with what is in the database
    // add click listener to the images
    updateStats()
    ivPaper.setOnClickListener { createGame(Move.PAPER) }
    ivRock.setOnClickListener { createGame(Move.ROCK) }
    ivScissors.setOnClickListener { createGame(Move.SCISSORS) }
  }

  /**
   * method to create a game takes player move as parameter
   */
  private fun createGame(playerMove: Move) {
    // botmove is random number between 0 and 2 and is casted to enum move
    // this in order to be stored in the database
    // created game for Game model which holds the date, playermove and botmove
    // added matchresult to game model, this based on win lose check
    mainScope.launch {
      var botMove = (0..2).random().toEnum<Move>()

      var game = Game(
        date = Calendar.getInstance().time,
        playerMove = playerMove,
        botMove = botMove
      )
      game.matchResult = winLoseCheck(playerMove, botMove)
      // use Dispatchers IO for database call
      // insert new game into database
      withContext(Dispatchers.IO) {
        gameRepository.insertGame(game)
        Log.i("database", "added game to database: $game")
      }
      // update activity with new game
      // update stats method called
      updateGame(game)
      updateStats()
    }
  }

  /**
   * method to check the win
   */
  private fun winLoseCheck(playerMove: Move, botMove: Move): MatchResult {
    if (playerMove == botMove) return MatchResult.DRAW

    return if (playerMove == Move.SCISSORS && botMove == Move.ROCK) {
      MatchResult.LOSE
    } else if (playerMove == Move.ROCK && botMove == Move.PAPER) {
      MatchResult.LOSE
    } else if (playerMove == Move.PAPER && botMove == Move.SCISSORS) {
      MatchResult.LOSE
    } else {
      MatchResult.WIN
    }
  }

  /**
   * method to update the acitvity after match is played
   */
  private fun updateGame(game: Game) {
    // check what player move was given
    when (game.playerMove) {
      Move.SCISSORS -> ivPicked.setImageResource(R.drawable.scissors)
      Move.PAPER -> ivPicked.setImageResource(R.drawable.paper)
      Move.ROCK -> ivPicked.setImageResource(R.drawable.rock)
    }
    // check what botmove was given
    when (game.botMove) {
      Move.SCISSORS -> ivComputerPicked.setImageResource(R.drawable.scissors)
      Move.PAPER -> ivComputerPicked.setImageResource(R.drawable.paper)
      Move.ROCK -> ivComputerPicked.setImageResource(R.drawable.rock)
    }
    // check the matchresult
    when (game.matchResult) {
      MatchResult.WIN -> tvWinLose.text = this.resources.getString(R.string.Winner)
      MatchResult.LOSE -> tvWinLose.text = this.resources.getString(R.string.Loser)
      MatchResult.DRAW -> tvWinLose.text = this.resources.getString(R.string.Draw)
    }
  }

  /**
   * method to update the game stats
   */
  private fun updateStats() {
    // get the gameWIns gameLoses and Draws as variables to be used in the textfield
    mainScope.launch {
      val gameWins = withContext(Dispatchers.IO) {
        gameRepository.getWins()
      }
      val gameLoses = withContext(Dispatchers.IO) {
        gameRepository.getLose()
      }
      val gameDraws = withContext(Dispatchers.IO) {
        gameRepository.getDraws()
      }
      tvStats.text = getString(R.string.stat_show, gameWins, gameLoses,gameDraws)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // handle the click and go to history activity
    return when (item.itemId) {
      R.id.ic_history_white -> {
        var intent = Intent(this, History::class.java)
        startActivityForResult(intent, DATA)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  // inline function to be able to cast Int to Enum needed for move
  private inline fun <reified Move : Enum<Move>> Int.toEnum(): Move = enumValues<Move>()[this]

  /**
   * companion object with standard value 100
   */
  companion object {
    const val DATA = 100
  }
}

