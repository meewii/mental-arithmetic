package com.meewii.mentalarithmetic.ui

import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity(@LayoutRes private val layoutRes: Int = -1): AppCompatActivity(), LifecycleRegistryOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        if (layoutRes != -1) setContentView(layoutRes)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected fun <T : ViewModel> getViewModel(clazz: Class<T>): ViewModel = ViewModelProviders.of(this, viewModelFactory).get(clazz)

}