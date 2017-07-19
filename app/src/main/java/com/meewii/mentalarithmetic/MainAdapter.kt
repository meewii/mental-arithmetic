package com.meewii.mentalarithmetic

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.models.Operation
import kotlinx.android.synthetic.main.list_item_past_formula.view.*


class MainAdapter(operations: MutableList<Operation>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var mOperations: MutableList<Operation> = operations

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item_past_formula, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val operation = mOperations[position]
        val pastFormula : String = operation.getFullOperation()
        holder.pastFormula.text = pastFormula
    }

    override fun getItemCount(): Int {
        return mOperations.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pastFormula = view.pastFormula!!
    }

}