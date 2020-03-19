package com.example.rockpaperscissors.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.models.Game

class GameHistoryAdapter(private val playedGames: List<Game>): RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHistoryAdapter.ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
    )
  }

  override fun getItemCount(): Int = playedGames.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(playedGames[position])

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(game: Game) {

    }
  }
}
