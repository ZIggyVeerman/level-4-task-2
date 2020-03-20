package com.example.rockpaperscissors.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.enums.MatchResult
import com.example.rockpaperscissors.enums.Move
import com.example.rockpaperscissors.models.Game
import kotlinx.android.synthetic.main.item_game.view.*
import java.util.*

class GameHistoryAdapter(private val playedGames: List<Game>): RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {
  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(game: Game) {
      //create date property and assign it to a textfield
      val date = Calendar.getInstance().time
      itemView.tvDateTime.text = date.toString()
      //checks what the match result was based on this shows the user a message
      when (game.matchResult) {
        MatchResult.DRAW -> {
          itemView.tvWinLoseOrDraw.text = itemView.resources.getString(R.string.Draw)
        }
        MatchResult.WIN -> {
          itemView.tvWinLoseOrDraw.text = itemView.resources.getString(R.string.Winner)
        }
        else -> {
          itemView.tvWinLoseOrDraw.text = itemView.resources.getString(R.string.Loser)
        }
      }
      //checks what player move has been made and edits the image given based on this
      when (game.playerMove) {
        Move.ROCK -> itemView.ivPickedHistory.setImageResource(R.drawable.rock)
        Move.PAPER -> itemView.ivPickedHistory.setImageResource(R.drawable.paper)
        Move.SCISSORS -> itemView.ivPickedHistory.setImageResource(R.drawable.scissors)
      }
      //checks what bot move has been made and edits the image given based on this
      when (game.botMove) {
        Move.ROCK -> itemView.ivComputerPickedHistory.setImageResource(R.drawable.rock)
        Move.PAPER -> itemView.ivComputerPickedHistory.setImageResource(R.drawable.paper)
        Move.SCISSORS -> itemView.ivComputerPickedHistory.setImageResource(R.drawable.scissors)
      }
    }
  }

  /**
   * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
   */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHistoryAdapter.ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
    )
  }
  /**
   * Returns the size of the list
   */
  override fun getItemCount(): Int = playedGames.size

  /**
   * Called by RecyclerView to display the data at the specified position.
   */
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(playedGames[position])
  }
}
