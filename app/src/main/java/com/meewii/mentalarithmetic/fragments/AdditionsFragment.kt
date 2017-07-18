package com.meewii.mentalarithmetic.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.MainAdapter
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.models.Operation
import com.meewii.mentalarithmetic.models.OperationType
import kotlinx.android.synthetic.main.fragment_additions.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import java.util.*

class AdditionsFragment : Fragment() {

    private var myAdapter: MainAdapter? = null
    private var mutableList: MutableList<Operation>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_additions, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentFormula.setText(R.string.additions)

        initialize()
        setupList()
        setUpCalculator()
//        loadData()
    }

    private fun initialize() {
        mutableList = mutableListOf()
        layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        myAdapter = MainAdapter(mutableList!!)
    }

    private fun setupList() {
        pastFormulaList!!.layoutManager = layoutManager
        pastFormulaList!!.adapter = myAdapter
    }

//    private fun loadData() {
//        for (i in 0..9) {
//            val formula:String = "2+"+i
//            val solution:Int = 2.0+i
//            val myItem = Operation(OperationType.ADDITION, formula, solution)
//            mutableList!!.add(myItem)
//        }
//        myAdapter!!.notifyDataSetChanged()
//    }

    private fun setUpCalculator() {
        val operandA: Int = Random().nextInt(100 - 1) + 1;
        val operandB: Int = Random().nextInt(100 - 1) + 1;


    }

}