package com.meewii.mentalarithmetic.ui.game

import android.os.Bundle
import android.view.View
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.ui.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_operation.*
import kotlinx.android.synthetic.main.content_game.*
import org.jetbrains.anko.toast

class GameActivity : BaseActivity(R.layout.activity_game) {

    private val gameViewModel by lazy { getViewModel(GameViewModel::class.java) as GameViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        observeLiveData(gameViewModel.helloData) { operation_field.text = it }
        observeLiveData(gameViewModel.helloDataState) { it?.apply { onHandleDataState(this) } }
    }

    override fun onStart() {
        super.onStart()
        operation_field.setOnClickListener { gameViewModel.refreshHelloMessage() }
    }

    private fun onHandleDataState(state: GameViewModel.HelloDataState) {
        when (state) {
            is GameViewModel.HelloDataState.ACTIVE -> {
                operation_field.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

            is GameViewModel.HelloDataState.BUSY -> {
                operation_field.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }

            is GameViewModel.HelloDataState.ERROR -> {
                toast(state.message)
                operation_field.apply {
                    text = "Click to Retry"
                    visibility = View.VISIBLE
                }
                progressBar.visibility = View.GONE
            }
        }
    }
}
