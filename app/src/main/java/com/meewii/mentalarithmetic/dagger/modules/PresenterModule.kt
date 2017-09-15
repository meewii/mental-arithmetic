package com.meewii.mentalarithmetic.dagger.modules

import android.content.Context
import com.meewii.mentalarithmetic.presenters.AdditionsPresenter
import dagger.Module
import dagger.Provides


@Module
class PresenterModule {

    // Add @Provides annotated method in PresenterModule for each SOURCE object to be injected.
    @Provides
    internal fun provideAdditionsPresenter(context: Context): AdditionsPresenter =
            AdditionsPresenter(context)

}

