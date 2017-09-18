package com.meewii.mentalarithmetic.ui

import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.Status
import kotlinx.android.synthetic.main.list_item_past_formula.view.*


class OperationAdapter(private val context: Context) : RecyclerView.Adapter<OperationAdapter.ViewHolder>() {

    private val TYPE_SUCCESS: Int = 0
    private val TYPE_FAIL: Int = 1

    private lateinit var operations: MutableList<Operation>

    fun addOperations(operations: MutableList<Operation>): OperationAdapter {
        this.operations = operations
        return this
    }

    override fun getItemViewType(position: Int): Int {
        return if (operations[position].status == Status.SUCCESS) {
            TYPE_SUCCESS
        } else {
            TYPE_FAIL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item_past_formula, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val operation: Operation = operations[position]
        val fullUserOperation: String = operation.getFullUserOperation()
        holder.pastFormula.text = fullUserOperation

        var color: Int = R.color.operation_success
        when (getItemViewType(position)) {
            TYPE_FAIL -> color = R.color.operation_fail
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.pastFormula.setTextColor(context.resources.getColor(color, null))
        } else {
            holder.pastFormula.setTextColor(ContextCompat.getColor(context, color))
        }
    }

    override fun getItemCount(): Int = operations.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pastFormula = view.pastFormula!!
    }

}