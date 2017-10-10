package com.meewii.mentalarithmetic.ui.stats

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.ui.BaseFragment
import com.meewii.mentalarithmetic.ui.score.StatsAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_score.*

class StatsFragment : BaseFragment(R.layout.fragment_score) {

    override fun getLifecycle(): LifecycleRegistry = LifecycleRegistry(this@StatsFragment)

    private val scoreViewModel by lazy { getViewModel(StatsViewModel::class.java) as StatsViewModel }
    private var sectionId: Int = 0
    private lateinit var scoreAdapter: StatsAdapter

    companion object {

        val sectionsMap = mapOf(
                Pair(0, Difficulty.VERY_EASY),
                Pair(1, Difficulty.EASY),
                Pair(2, Difficulty.MEDIUM),
                Pair(3, Difficulty.HARD),
                Pair(4, Difficulty.VERY_HARD)
        )

        // The fragment argument representing the section id for this fragment.
        private val SECTION_ID_ARG = "section_id"

        // Returns a new instance of this fragment for the given ID.
        fun newInstance(sectionId: Int): StatsFragment {
            val fragment = StatsFragment()
            val args = Bundle()
            args.putInt(SECTION_ID_ARG, sectionId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = object : StatsAdapter.OnItemClickListener {
            override fun onItemClick(item: ScoreEntry) {
                Log.d(Const.APP_TAG, "[ScoreFragment#onItemClick()] Clicked on $item")
            }
        }

        observeLiveData()
        setUpAdapter(scoreViewModel.liveScoreList, listener)
    }

    private fun observeLiveData() {
        // get the Fragment's section id to recover the proper data
        sectionId = arguments.getInt(SECTION_ID_ARG)
        // Get difficulty per section id
        val difficulty = sectionsMap[sectionId]
        // Get and observe data per difficulty
        scoreViewModel
                .loadScoreList(difficulty)
                .observe(this,
                        Observer<List<ScoreEntry>> { scoreList ->
                            Log.v(Const.APP_TAG, "[ScoreFragment#observeLiveData()] " +
                                    "Observe for section#$sectionId score list: ${scoreList?.size}")
                            if (scoreList != null) refreshList()
                        }
                )
    }

    /**
     * Prepare the RecyclerView to receive the list of Scores
     */
    private fun setUpAdapter(list: MutableLiveData<List<ScoreEntry>>, listener: StatsAdapter.OnItemClickListener) {
        scoreAdapter = StatsAdapter(list.value, listener)
        recyclerView.adapter = scoreAdapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    /**
     * Update the list with new data
     */
    private fun refreshList() {
        scoreAdapter.notifyDataSetChanged()
    }
}