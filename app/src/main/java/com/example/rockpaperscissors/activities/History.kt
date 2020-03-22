package com.example.rockpaperscissors.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.adapters.GameHistoryAdapter
import com.example.rockpaperscissors.models.Game
import com.example.rockpaperscissors.repositories.GameRepository
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class History : AppCompatActivity() {
  private lateinit var gameRepository: GameRepository
  private val mainScope = CoroutineScope(Dispatchers.Main)
  private var listOFGames = arrayListOf<Game>()
  private var gameHistoryAdapter = GameHistoryAdapter(listOFGames)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_history)
    setSupportActionBar(toolbarHistory)
    //initialize the gameRepository and give it the activity context
    gameRepository = GameRepository(this)

    initViews()
  }

  /**
   * Initviews method to create all things that have to start on create
   */
  private fun initViews() {
    supportActionBar?.title = "Game History"
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    // Initialize the recycler view with a linear layout manager, adapter
    rvHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    rvHistory.adapter = gameHistoryAdapter
    rvHistory.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    getGames()
  }

  /**
   * method to delete game history
   */
  private fun deleteHistory() {
    // Coroutinescope laucnh to delete all games
    // context use of Dispartchers IO to do database work
    mainScope.launch {
      withContext(Dispatchers.IO) {
        gameRepository.deleteAllGamesFromHistory()
      }
    }
    // update games after all are deleted
    getGames()
  }

  /**
   * method to get games played
   */
  private fun getGames() {
    // Coroutinescope launch to get game history
    // variable gameHistory is set to be used
    // context use of Dispartchers IO to do database work
    mainScope.launch {
      val gameHistory = withContext(Dispatchers.IO) {
        gameRepository.getAllGames()
      }
      // if list is empty add game at the top and notify that the set has changed
      // if list was not empty clear list to not get doubles and add game, after this notify data set changed
      if (listOFGames.isEmpty()) {
        listOFGames.addAll(gameHistory)
        gameHistoryAdapter.notifyDataSetChanged()
      } else {
        listOFGames.clear()
        listOFGames.addAll(gameHistory)
        gameHistoryAdapter.notifyDataSetChanged()
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_history, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button
    // if delete button is clicked deleteHistory will be  called
    return when (item.itemId) {
      R.id.ic_delete_white -> {
        deleteHistory()
        true
      }
      android.R.id.home -> {
        var intent = Intent(this, MainActivity::class.java)
        startActivityForResult(intent, HISTORY_DATA)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  /**
   * companion object with standard value of 100
   */
  companion object {
    const val HISTORY_DATA = 100
  }
}
