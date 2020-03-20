package com.example.rockpaperscissors.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.enums.Move
import com.example.rockpaperscissors.enums.MatchResult
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Date
import java.util.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbarMain)

    initViews()
  }

  private fun initViews() {
    ivPaper.setOnClickListener{createGame(Move.PAPER)}
    ivRock.setOnClickListener{createGame(Move.ROCK)}
    ivScissors.setOnClickListener{createGame(Move.SCISSORS)}
  }

  private fun createGame(playerMove: Move) {
    val date = Calendar.getInstance().time
    println(date)

//    TODO("add some database stuff")
//    TODO("date now")
//    TODO("handle computer move randomize")
//    TODO("create function to handle move given to change image etc")
  }

  private fun winLoseCheck(playerMove: Move, computerMove: Move): MatchResult {
    return when(setOf(playerMove, computerMove)) {
      setOf(Move.ROCK, Move.ROCK) -> MatchResult.DRAW
      setOf(Move.PAPER, Move.PAPER) -> MatchResult.DRAW
      setOf(Move.SCISSORS, Move.SCISSORS) -> MatchResult.DRAW
      setOf(Move.ROCK, Move.PAPER) -> MatchResult.LOSE
      setOf(Move.PAPER, Move.SCISSORS) -> MatchResult.LOSE
      setOf(Move.SCISSORS, Move.ROCK) -> MatchResult.LOSE
      else -> MatchResult.WIN
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
  companion object{
    const val DATA = 0
  }
}
