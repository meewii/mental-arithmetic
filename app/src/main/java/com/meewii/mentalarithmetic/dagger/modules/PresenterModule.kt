package com.meewii.mentalarithmetic.dagger.modules

import android.content.Context
import com.meewii.mentalarithmetic.presenters.AdditionsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PresenterModule {

    // Add @Provides annotated method in PresenterModule for each SOURCE object to be injected.
    @Provides
    @Singleton
    internal fun provideAdditionsPresenter(context: Context): AdditionsPresenter =
            AdditionsPresenter(context)

}

