package com.meewii.mentalarithmetic.dagger.components

import com.meewii.mentalarithmetic.dagger.modules.ActivityModule
import com.meewii.mentalarithmetic.dagger.scopes.ActivityScope
import com.meewii.mentalarithmetic.ui.activities.BaseNavActivity
import com.meewii.mentalarithmetic.ui.activities.MainActivity
import com.meewii.mentalarithmetic.ui.activities.NewGameActivity
import com.meewii.mentalarithmetic.ui.fragments.AdditionsFragment
import com.meewii.mentalarithmetic.ui.fragments.DivisionsFragment
import com.meewii.mentalarithmetic.ui.fragments.MultiplicationsFragment
import com.meewii.mentalarithmetic.ui.fragments.SubtractionsFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(ActivityModule::class))
@ActivityScope
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(additionsFragment: AdditionsFragment)
    fun inject(multiplicationsFragment: MultiplicationsFragment)
    fun inject(subtractionsFragment: SubtractionsFragment)
    fun inject(divisionsFragment: DivisionsFragment)

    fun inject(baseNavActivity: BaseNavActivity)
}