package com.meewii.mentalarithmetic.dagger.modules

import android.content.Context
import com.meewii.mentalarithmetic.dagger.qualifiers.ForActivity
import com.meewii.mentalarithmetic.ui.activities.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @ForActivity
    fun provideActivityContext(): Context = activity

}