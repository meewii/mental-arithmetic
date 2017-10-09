package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.ui.nav.HomeNavActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_game.*


class ScoredGameActivity : BaseGameActivity() {

    private val gameViewModel by lazy { getViewModel(ScoredGameViewModel::class.java) as ScoredGameViewModel }

    private val registry = LifecycleRegistry(this)
    override fun getLifecycle(): LifecycleRegistry = registry

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setUpView(gameViewModel)

        observeLiveCurrentOperation(gameViewModel)
        observeLiveOperationList(gameViewModel)
        observeLiveEditTextState(gameViewModel)
        observeLiveScore()
    }

    /**
     * Observe the score of the game, specific to ScoredGameViewModel
     */
    private fun observeLiveScore() {
        gameViewModel.liveGameState.observe(this, Observer<ScoredGameViewModel.GameState> { state ->
            when (state) {
                ScoredGameViewModel.GameState.OVER -> {
                    hideSoftKeyboard()
                    inputContainer.visibility = View.INVISIBLE
                    val gameOverBar = Snackbar
                            .make(container, "Game over! Points: ${gameViewModel.liveScore.value?.points}", Snackbar.LENGTH_INDEFINITE)
                            .setAction("New game?") {
                                gameViewModel.resetAllLiveData()
                            }
                    gameOverBar.show()
                }
                ScoredGameViewModel.GameState.NEW -> {
                    inputContainer.visibility = View.VISIBLE
                    showSoftKeyboard()
                }
                else -> {
                }
            }
        })
    }

    /**
     * On back pressed, we check if the game is over, prompt the user if they really want to quit
     * the game and save its score if he wants to quit
     */
    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    override fun onBackPressed() {
        if(gameViewModel.isGameOver()) {
            gameViewModel.clearGame()
            goToMainPage()
            return
        }

        val dialogBuilder = AlertDialog.Builder(this@ScoredGameActivity)
        dialogBuilder.setMessage(R.string.prompt_quit_game)
                .setPositiveButton(R.string.yes, { dialog, id ->
                    gameViewModel.saveCurrentScore()
                    gameViewModel.clearGame()
                    dialog.dismiss()
                    goToMainPage()
                })
                .setNegativeButton(R.string.cancel, { dialog, id ->
                    dialog.dismiss()
                })
        dialogBuilder.create().show()
    }

    private fun goToMainPage() {
        val intent = Intent(this@ScoredGameActivity, HomeNavActivity::class.java)
        // The keyword "or" is a bit confusing but all flags will be used
        intent.flags = FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
