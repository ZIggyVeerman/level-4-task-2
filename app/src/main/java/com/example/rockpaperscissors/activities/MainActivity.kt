package com.example.rockpaperscissors.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    gameRepository = GameRepository(this)

    initViews()
  }

  private fun initViews() {
    ivPaper.setOnClickListener{createGame(Move.PAPER)}
    ivRock.setOnClickListener{createGame(Move.ROCK)}
    ivScissors.setOnClickListener{createGame(Move.SCISSORS)}
  }

  private fun createGame(playerMove: Move) {
    mainScope.launch {
      var botMove = (0..2).random().toEnum<Move>()

      var game = Game(
        date = Calendar.getInstance().time,
        playerMove = playerMove,
        botMove = botMove
      )
      game.matchResult = winLoseCheck(playerMove, botMove)

      withContext(Dispatchers.IO) {
        gameRepository.insertGame(game)
      }
    }
  }

  private fun winLoseCheck(playerMove: Move, botMove: Move): MatchResult {
    if (playerMove == botMove) return MatchResult.DRAW

    return if (playerMove == Move.SCISSORS && botMove == Move.ROCK) {
      MatchResult.LOSE
    }else if (playerMove == Move.ROCK && botMove == Move.PAPER){
      MatchResult.LOSE
    }else if (playerMove == Move.PAPER && botMove == Move.SCISSORS){
      MatchResult.LOSE
    } else {
      MatchResult.WIN
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.ic_history_white -> {
        var intent = Intent(this, History::class.java)
        startActivityForResult(intent, DATA)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
  private inline fun <reified Move : Enum<Move>> Int.toEnum(): Move = enumValues<Move>()[this]

  companion object{
    const val DATA = 0
  }
}

