package com.meewii.mentalarithmetic.modules.statistics.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.meewii.mentalarithmetic.modules.statistics.views.StatsFragment
import com.meewii.mentalarithmetic.modules.statistics.views.StatsFragment.Companion.sectionsMap

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class StatsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // getItem is called to instantiate the fragment for the given page.
    // Return a PlaceholderFragment (defined as a static inner class below).
    override fun getItem(position: Int): Fragment = StatsFragment.newInstance(position)

    override fun getCount(): Int = 4

    override fun getPageTitle(position: Int): CharSequence? {
        if(sectionsMap[position] != null) {
            return sectionsMap[position]!!.displayName
        }
        return null
    }

}