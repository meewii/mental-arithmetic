package com.meewii.mentalarithmetic.ui.score

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_score.*
import javax.inject.Inject

class ScoreFragment : Fragment() {

    @Inject
    lateinit var presenter: ScorePresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_score, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentId = arguments.getInt(FRAGMENT_ID_ARG)
        val scoreList = when(fragmentId) {
            0 -> presenter.getScoresVeryEasy()
            1 -> presenter.getScoresEasy()
            2 -> presenter.getScoresMedium()
            3 -> presenter.getScoresHard()
            4 -> presenter.getScoresVeryHard()
            else -> presenter.getScoresEasy()
        }

        val listener = object : ScoreAdapter.OnItemClickListener {
            override fun onItemClick(item: ScoreEntry) {
                Log.d("ScoreActivity", "Clicked on $item")
            }
        }

        recyclerView.adapter = ScoreAdapter(scoreList, listener)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(activity)

        presenter.attachView(this@ScoreFragment)
    }

    companion object {
        // The fragment argument representing the ID for this fragment.
        private val FRAGMENT_ID_ARG = "fragment_id"

        // Returns a new instance of this fragment for the given ID.
        fun newInstance(fragmentId: Int): ScoreFragment {
            Log.d("ScoreFragment", "Fragment's id: $fragmentId")

            val fragment = ScoreFragment()
            val args = Bundle()
            args.putInt(FRAGMENT_ID_ARG, fragmentId)
            fragment.arguments = args
            return fragment
        }
    }
}