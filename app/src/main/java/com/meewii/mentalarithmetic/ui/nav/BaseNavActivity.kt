package com.meewii.mentalarithmetic.ui.nav

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_nav.*


open class BaseNavActivity : BaseActivity() {

    lateinit protected var navItems: Array<String>
    lateinit protected var listener: NavAdapter.OnItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        // toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
        recyclerView.adapter = NavAdapter(navItems, listener)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = layoutManager
    }

}
