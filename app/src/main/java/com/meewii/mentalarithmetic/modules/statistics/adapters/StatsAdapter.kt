package com.meewii.mentalarithmetic.modules.statistics.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.models.Statistic
import kotlinx.android.synthetic.main.list_item_stats.view.*

class StatsAdapter(
        private val context: Context,
        private val scoreList: List<Statistic>?,
        private val listener: OnItemClickListener) :
        RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Statistic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StatsViewHolder(context, layoutInflater.inflate(R.layout.list_item_stats, parent, false))
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        if(scoreList != null)
            holder.bind(scoreList[position], listener)
    }

    override fun getItemCount(): Int = when(scoreList) {
        null -> 0
        else -> scoreList.size
    }

    class StatsViewHolder(val context: Context, val view: View) : RecyclerView.ViewHolder(view) {
        private val difficultyField = view.difficulty_field!!
        private val gameCountField = view.games_count_field!!
        private val fastestField = view.fastest_field!!
        private val averageField = view.average_field!!
        private val slowestField = view.slowest_field!!

        fun bind(statistic: Statistic, listener: OnItemClickListener) {
            this.difficultyField.text = statistic.difficulty.displayName
            this.gameCountField.text = context.resources.getQuantityString(R.plurals.games, statistic.gameCount, statistic.gameCount)
            this.fastestField.text =  String.format(context.resources.getString(R.string.fastest_duration), statistic.fastestDuration)
            this.averageField.text =  String.format(context.resources.getString(R.string.average_duration), statistic.averageDuration)
            this.slowestField.text =  String.format(context.resources.getString(R.string.slowest_duration), statistic.slowestDuration)

            view.setOnClickListener({
                listener.onItemClick(statistic)
            })
        }
    }

}