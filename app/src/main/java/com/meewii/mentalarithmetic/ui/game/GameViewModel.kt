package com.meewii.mentalarithmetic.ui.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GameViewModel @Inject constructor(val gameRepository: GameRepository) : ViewModel() {

    val helloData = MutableLiveData<String>()
    val helloDataState = MutableLiveData<HelloDataState>()
    private var counter: Int = 0

    fun refreshHelloMessage() {
        disposables.add(gameRepository.fetchHello()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { helloDataState.value = HelloDataState.BUSY }
                .doOnSuccess {
                    counter++
                    helloDataState.value = HelloDataState.ACTIVE
                }
                .subscribe(
                        {
                            helloData.value = """$it (${counter})
                |(Click to Refresh)""".trimMargin()
                        },
                        { helloDataState.value = HelloDataState.ERROR("Failed to say Hello!") }
                )
        )
    }

    sealed class HelloDataState {
        object ACTIVE : HelloDataState()
        object BUSY : HelloDataState()
        data class ERROR(val message: String) : HelloDataState()
    }

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.dispose()
    }
}