package com.meewii.mentalarithmetic.ui.game

import android.app.Application
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(val application: Application) {

    fun fetchHello() = Single.just("Hello World")!!
}