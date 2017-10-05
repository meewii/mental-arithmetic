package com.meewii.mentalarithmetic.ui.score

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meewii.mentalarithmetic.R
import com.meewii.mentalarithmetic.data.database.ScoreEntry
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.ui.BaseActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class ScoreActivity : BaseActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        override fun getItem(position: Int): Fragment =
                ScoreFragment.newInstance(position)

        override fun getCount(): Int = 5

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return Difficulty.VERY_EASY.displayName
                1 -> return Difficulty.EASY.displayName
                2 -> return Difficulty.MEDIUM.displayName
                3 -> return Difficulty.HARD.displayName
                4 -> return Difficulty.VERY_HARD.displayName
            }
            return null
        }
    }

    class ScoreFragment : Fragment() {

        @Inject
        lateinit var presenter: ScorePresenter

//        override fun onAttach(context: Context?) {
//            super.onAttach(context)
//            if (context is BaseActivity) {
//                context.activityComponent.inject(this@ScoreFragment)
//            } else {
//                throw Exception("Context is not BaseActivity")
//            }
//        }

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? =
                inflater!!.inflate(R.layout.fragment_score, container, false)

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val recyclerView = view?.findViewById(R.id.recyclerView) as RecyclerView

            val section = arguments.getInt(ARG_SECTION_NUMBER)
            val scoreList = when(section) {
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
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): ScoreFragment {
                Log.d("ScoreFragment", "sectionNumber: $sectionNumber")

                val fragment = ScoreFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}