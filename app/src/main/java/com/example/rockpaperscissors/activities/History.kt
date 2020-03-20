package com.example.rockpaperscissors.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.adapters.GameHistoryAdapter
import com.example.rockpaperscissors.models.Game
import com.example.rockpaperscissors.repositories.GameRepository
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
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

    gameRepository = GameRepository(this)

    initViews()
  }

  private fun initViews(){
    supportActionBar?.title = "Game History"
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    rvHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    rvHistory.adapter = gameHistoryAdapter
//    getGames()
  }
  private fun deleteHistory() {
    //delete all games
    mainScope.launch {
      withContext(Dispatchers.IO) {
        gameRepository.deleteAllGamesFromHistory()
      }
    }
    // update games after all are deleted
//    getGames()
  }

  private fun getGames() {
    mainScope.launch {
      val gameHistory = withContext(Dispatchers.IO){
        gameRepository.getAllGames()
      }
      if (listOFGames.isEmpty()){
        listOFGames.addAll(gameHistory)
        gameHistoryAdapter.notifyDataSetChanged()
      }else{
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
  companion object{
    const val HISTORY_DATA = 1
  }
}
