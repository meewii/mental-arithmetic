package com.meewii.mentalarithmetic

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.meewii.mentalarithmetic.activities.CreditsActivity
import com.meewii.mentalarithmetic.fragments.AdditionsFragment
import com.meewii.mentalarithmetic.fragments.DivisionsFragment
import com.meewii.mentalarithmetic.fragments.MultiplicationsFragment
import com.meewii.mentalarithmetic.fragments.SubtractionsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        selectFragment(AdditionsFragment())

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var fragment: Fragment? = null
        var activity: Activity? = null

        when (item.itemId) {
            R.id.additions -> {
                fragment = AdditionsFragment()
            }
            R.id.subtractions -> {
                fragment = SubtractionsFragment()
            }
            R.id.multiplications -> {
                fragment = MultiplicationsFragment()
            }
            R.id.divisions -> {
                fragment = DivisionsFragment()
            }

            R.id.about -> {
                activity = CreditsActivity()
            }

            R.id.settings -> {
                // TODO
            }
        }

        if(activity != null) {
            selectActivity(activity)
        } else {
            if(fragment == null) fragment = AdditionsFragment()
            selectFragment(fragment)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun selectActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    fun selectFragment(fragment: Fragment) {
        val fragmentManager = fragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }
}