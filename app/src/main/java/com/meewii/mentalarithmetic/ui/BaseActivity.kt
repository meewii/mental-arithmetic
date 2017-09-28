package com.meewii.mentalarithmetic.ui

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity(@LayoutRes private val layoutRes: Int = -1): AppCompatActivity(), LifecycleRegistryOwner {

    @Inject lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        if (layoutRes != -1) setContentView(layoutRes)
    }

    protected fun <T : ViewModel> getViewModel(clazz: Class<T>): ViewModel = ViewModelProviders.of(this, vmFactory).get(clazz)

    protected fun <T> observeLiveData(data: LiveData<T>, action: (T?) -> Unit) {
        data.observe(this, Observer<T>(action))
    }

    private val registry = LifecycleRegistry(this)
    override fun getLifecycle(): LifecycleRegistry = registry

}