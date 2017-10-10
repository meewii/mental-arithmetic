package com.meewii.mentalarithmetic.ui.score

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import kotlinx.android.synthetic.main.list_item_score.view.*

class StatsAdapter(private val scoreList: List<ScoreEntry>?, private val listener: OnItemClickListener) :
        RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: ScoreEntry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StatsViewHolder(layoutInflater.inflate(R.layout.list_item_score, parent, false))
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        if(scoreList != null)
            holder.bind(scoreList[position], listener)
    }

    override fun getItemCount(): Int = when(scoreList) {
        null -> 0
        else -> scoreList.size
    }

    class StatsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val pointsField = view.points_field!!
        private val idField = view.id_field!!
        private val difficultyField = view.difficulty_field!!
        private val operatorField = view.operator_field!!
        private val userField = view.user_field!!

        fun bind(score: ScoreEntry, listener: OnItemClickListener) {
            this.pointsField.text = score.points.toString()
            this.idField.text = score.id.toString()
            this.difficultyField.text = score.difficulty.displayName
            this.operatorField.text = score.operator.displayName
            this.userField.text = score.user_id.toString()
            view.setOnClickListener({
                listener.onItemClick(score)
            })
        }
    }

}