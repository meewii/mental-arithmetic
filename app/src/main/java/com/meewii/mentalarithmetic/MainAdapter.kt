package com.meewii.mentalarithmetic

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Status
import kotlinx.android.synthetic.main.list_item_past_formula.view.*


class MainAdapter(context: Context, operations: MutableList<Operation>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    val TYPE_SUCCESS: Int = 0
    val TYPE_FAIL: Int = 1

    val mContext = context
    var mOperations: MutableList<Operation> = operations

    override fun getItemViewType(position: Int): Int {
        if(mOperations[position].status == Status.SUCCESS) {
            return TYPE_SUCCESS
        } else {
            return TYPE_FAIL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item_past_formula, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val operation: Operation = mOperations[position]
        val fullUserOperation : String = operation.getFullUserOperation()
        holder.pastFormula.text = fullUserOperation

        var color: Int = R.color.operation_success
        when(getItemViewType(position)) {
            TYPE_FAIL -> color = R.color.operation_fail
        }
        holder.pastFormula.setTextColor(mContext.resources.getColor(color, null))
    }

    override fun getItemCount(): Int {
        return mOperations.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pastFormula = view.pastFormula!!
    }

}