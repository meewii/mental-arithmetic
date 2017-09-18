package com.meewii.mentalarithmetic.dagger.components

import com.meewii.mentalarithmetic.core.App
import com.meewii.mentalarithmetic.dagger.modules.AppModule
import com.meewii.mentalarithmetic.ui.fragments.AdditionsFragment
import com.meewii.mentalarithmetic.ui.fragments.DivisionsFragment
import com.meewii.mentalarithmetic.ui.fragments.MultiplicationsFragment
import com.meewii.mentalarithmetic.ui.fragments.SubtractionsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(application: App)
    fun inject(additionsFragment: AdditionsFragment)
    fun inject(multiplicationsFragment: MultiplicationsFragment)
    fun inject(subtractionsFragment: SubtractionsFragment)
    fun inject(divisionsFragment: DivisionsFragment)
}