package com.meewii.mentalarithmetic.modules.game.scored

import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.base.BaseGameActivity
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.modules.nav.views.HomeNavActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.content_game.*


class ScoredGameActivity : BaseGameActivity() {

    private val gameViewModel by lazy { getViewModel(ScoredGameViewModel::class.java) as ScoredGameViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setUpView()

        observeLiveCurrentOperation(gameViewModel)
        observeLiveOperationList(gameViewModel)
        observeLiveEditTextState(gameViewModel)
        observeLiveGameDuration(gameViewModel)
        observeLiveGameState()
        observeLiveScore()
    }

    override fun onResume() {
        super.onResume()
        startTimer(gameViewModel)
    }

    private fun setUpView() {
        // Toolbar views
        scoreLayout.visibility = View.VISIBLE
        scoreView.text = resources.getQuantityString(R.plurals.points, 0, 0)
        livesView.text = resources.getQuantityString(R.plurals.remaining_lives, 5, 5)

        setUpView(gameViewModel)
    }

    /**
     * Observe if the game is ongoing or over, specific to ScoredGameViewModel
     */
    private fun observeLiveGameState() {
        gameViewModel.liveGameState.observe(this, Observer<ScoredGameViewModel.GameState> { state ->
            when (state) {
                ScoredGameViewModel.GameState.OVER -> {
                    // hide inputs
                    hideSoftKeyboard()
                    inputContainer.visibility = View.INVISIBLE
                    // stop timer
                    stopTimer()
                    // display prompt
                    val dialogBuilder = AlertDialog.Builder(this@ScoredGameActivity)
                    dialogBuilder
                            .setCancelable(false)
                            .setTitle(R.string.prompt_new_game_title)
                            .setMessage(R.string.prompt_new_game)
                            .setPositiveButton(R.string.yes, { dialog, id ->
                                gameViewModel.newGame()
                                dialog.dismiss()
                            })
                            .setNegativeButton(R.string.quit, { dialog, id ->
                                gameViewModel.clearGame()
                                dialog.dismiss()
                                goToMainPage()
                            })
                    dialogBuilder.create().show()
                }
                ScoredGameViewModel.GameState.NEW -> {
                    // show inputs
                    inputContainer.visibility = View.VISIBLE
                    showSoftKeyboard()
                    // restart timer
                    startTimer(gameViewModel)
                }
                else -> {
                }
            }
        })
    }

    /**
     * Observe the score of the game, specific to ScoredGameViewModel
     */
    private fun observeLiveScore() {
        gameViewModel.liveScore.observe(this, Observer<ScoreEntry> { score ->
            if(score == null) {
                scoreView.text = resources.getQuantityString(R.plurals.points, 0, 0)
                livesView.text = resources.getQuantityString(R.plurals.remaining_lives, 5, 5)
            } else {
                scoreView.text = resources.getQuantityString(R.plurals.points, score.points, score.points)
                val remainingLives: Int = ScoredGameViewModel.FAIL_LIMIT - score.failedOp
                livesView.text = resources.getQuantityString(R.plurals.remaining_lives, remainingLives, remainingLives)
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
