package com.meewii.mentalarithmetic.ui.score

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import kotlinx.android.synthetic.main.list_item_score.view.*

class ScoreAdapter(private val scoreList: List<ScoreEntry>, private val listener: OnItemClickListener) :
        RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: ScoreEntry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ScoreViewHolder(layoutInflater.inflate(R.layout.list_item_score, parent, false))
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(scoreList[position], listener)
    }

    override fun getItemCount(): Int = scoreList.size

    class ScoreViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val points = view.points!!

        fun bind(score: ScoreEntry, listener: OnItemClickListener) {
            this.points.text = score.points.toString()
            view.setOnClickListener({
                listener.onItemClick(score)
            })
        }
    }

}