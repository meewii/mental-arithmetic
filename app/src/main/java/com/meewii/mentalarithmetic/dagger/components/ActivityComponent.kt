package com.meewii.mentalarithmetic.dagger.components

import com.meewii.mentalarithmetic.dagger.modules.ActivityModule
import com.meewii.mentalarithmetic.dagger.scopes.ActivityScope
import com.meewii.mentalarithmetic.ui.nav.BaseNavActivity
import com.meewii.mentalarithmetic.ui.operations.OperationActivity
import com.meewii.mentalarithmetic.ui.score.ScoreActivity
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(ActivityModule::class))
@ActivityScope
interface ActivityComponent {
    fun inject(operationActivity: OperationActivity)
    fun inject(baseNavActivity: BaseNavActivity)
    fun inject(scoreActivity: ScoreActivity) {}
    fun inject(scoreFragment: ScoreActivity.ScoreFragment) {}
}