package com.meewii.mentalarithmetic.dagger.modules

import android.content.Context
import com.meewii.mentalarithmetic.dagger.qualifiers.ForActivity
import com.meewii.mentalarithmetic.ui.activities.BaseActivity
import dagger.Module
import dagger.Provides
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.meewii.mentalarithmetic.ui.adapters.OperationAdapter

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @ForActivity
    fun provideActivityContext(): Context = activity

    @Provides
    fun provideLayoutManager(@ForActivity context: Context): LinearLayoutManager =
            LinearLayoutManager(context, LinearLayout.VERTICAL, false)

    @Provides
    fun provideMainAdapter(@ForActivity context: Context): OperationAdapter =
            OperationAdapter(context)

}