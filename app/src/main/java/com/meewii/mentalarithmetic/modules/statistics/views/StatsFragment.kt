package com.meewii.mentalarithmetic.modules.statistics.views

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
import com.meewii.mentalarithmetic.base.BaseFragment
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.models.Statistic
import com.meewii.mentalarithmetic.modules.statistics.adapters.StatsAdapter
import com.meewii.mentalarithmetic.modules.statistics.viewmodels.StatsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_stats.*

class StatsFragment : BaseFragment(R.layout.fragment_stats) {

    override fun getLifecycle(): LifecycleRegistry = LifecycleRegistry(this@StatsFragment)

    private val scoreViewModel by lazy { getViewModel(StatsViewModel::class.java) as StatsViewModel }
    private var sectionId: Int = 0
    private lateinit var scoreAdapter: StatsAdapter

    companion object {

        val sectionsMap = mapOf(
                Pair(0, Operator.ADDITION),
                Pair(1, Operator.SUBTRACTION),
                Pair(2, Operator.MULTIPLICATION),
                Pair(3, Operator.DIVISION)
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
            override fun onItemClick(item: Statistic) {
                Log.d(Const.APP_TAG, "[ScoreFragment#onItemClick()] Clicked on $item")
            }
        }

        observeLiveData()
        setUpAdapter(scoreViewModel.liveScoreList, listener)
    }

    private fun observeLiveData() {
        // get the Fragment's section id to recover the proper data
        sectionId = arguments.getInt(SECTION_ID_ARG)
        // Get operator per section id
        val operator = sectionsMap[sectionId]
        // Get and observe data per difficulty


        scoreViewModel
                .loadStats(operator)
                .observe(this,
                        Observer<List<Statistic>> { scoreList ->
                            Log.v(Const.APP_TAG, "[ScoreFragment#observeLiveData()] " +
                                    "Observe for section#$sectionId score list: ${scoreList?.size}")
                            if (scoreList != null) refreshList()
                        }
                )
    }

    /**
     * Prepare the RecyclerView to receive the list of Scores
     */
    private fun setUpAdapter(list: MutableLiveData<List<Statistic>>, listener: StatsAdapter.OnItemClickListener) {
        scoreAdapter = StatsAdapter(activity, list.value, listener)
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