package com.meewii.mentalarithmetic.ui.fragments

import android.app.Fragment
import android.content.Context
import com.meewii.mentalarithmetic.dagger.components.ActivityComponent
import com.meewii.mentalarithmetic.ui.activities.BaseActivity

abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is BaseActivity) inject(context.activityComponent)
    }

    protected abstract fun inject(component: ActivityComponent)

}