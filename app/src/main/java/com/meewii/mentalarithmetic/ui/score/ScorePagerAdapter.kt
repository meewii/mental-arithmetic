package com.meewii.mentalarithmetic.ui.score

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.meewii.mentalarithmetic.ui.score.ScoreFragment.Companion.sectionsMap

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class ScorePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // getItem is called to instantiate the fragment for the given page.
    // Return a PlaceholderFragment (defined as a static inner class below).
    override fun getItem(position: Int): Fragment = ScoreFragment.newInstance(position)

    override fun getCount(): Int = 5

    override fun getPageTitle(position: Int): CharSequence? {
        if(sectionsMap[position] != null) {
            return sectionsMap[position]!!.displayName
        }
        return null
    }

}