package com.meewii.mentalarithmetic.ui.stats

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.R
import kotlinx.android.synthetic.main.list_item_stats.view.*

class StatsAdapter(private val scoreList: List<Stat>?, private val listener: OnItemClickListener) :
        RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Stat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StatsViewHolder(layoutInflater.inflate(R.layout.list_item_stats, parent, false))
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
        private val difficultyField = view.difficulty_field!!
        private val gameCountField = view.games_count_field!!
        private val fastestField = view.fastest_field!!
        private val averageField = view.average_field!!
        private val slowestField = view.slowest_field!!

        fun bind(stat: Stat, listener: OnItemClickListener) {
            this.difficultyField.text = stat.difficulty.displayName
            this.gameCountField.text = "${stat.gameCount} games"
            this.fastestField.text = "Fastest: ${stat.fastestDuration}"
            this.averageField.text = "Average: ${stat.averageDuration}"
            this.slowestField.text = "Slowest: ${stat.slowestDuration}"

            view.setOnClickListener({
                listener.onItemClick(stat)
            })
        }
    }

}