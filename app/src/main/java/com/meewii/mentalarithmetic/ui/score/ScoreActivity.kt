package com.meewii.mentalarithmetic.ui.score

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_score.*
import javax.inject.Inject

class ScoreActivity : BaseActivity() {

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var presenter: ScorePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // di
        activityComponent.inject(this)


        presenter.attachView(this@ScoreActivity)

        val scoreList = presenter.getScores()
        val listener = object : ScoreAdapter.OnItemClickListener {
            override fun onItemClick(item: ScoreEntry) {
                Log.d("ScoreActivity", "Clicked on $item")
            }
        }

        recyclerView.adapter = ScoreAdapter(scoreList, listener)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = layoutManager

    }
}