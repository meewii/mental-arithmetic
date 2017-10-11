package com.meewii.mentalarithmetic.base

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.modules.nav.NavAdapter
import kotlinx.android.synthetic.main.activity_nav.*


abstract class BaseNavActivity : BaseActivity(R.layout.activity_nav) {

    lateinit protected var navItems: Array<String>
    lateinit protected var listener: NavAdapter.OnItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView.adapter = NavAdapter(navItems, listener)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
    }

}
