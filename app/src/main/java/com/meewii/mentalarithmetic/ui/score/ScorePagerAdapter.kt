package com.meewii.mentalarithmetic.ui.score

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.meewii.mentalarithmetic.models.Difficulty

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class ScorePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

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