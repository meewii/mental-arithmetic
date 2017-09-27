package com.meewii.mentalarithmetic.dagger.components

import com.meewii.mentalarithmetic.dagger.modules.ActivityModule
import com.meewii.mentalarithmetic.dagger.scopes.ActivityScope
import com.meewii.mentalarithmetic.ui.activities.BaseNavActivity
import com.meewii.mentalarithmetic.ui.activities.MainActivity
import com.meewii.mentalarithmetic.ui.fragments.OperationFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(ActivityModule::class))
@ActivityScope
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(additionsFragment: OperationFragment)

    fun inject(baseNavActivity: BaseNavActivity)
}