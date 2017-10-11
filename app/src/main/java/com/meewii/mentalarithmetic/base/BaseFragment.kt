package com.meewii.mentalarithmetic.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes private val layoutRes: Int = -1): Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if(layoutRes != -1 ) {
            return inflater!!.inflate(layoutRes, container, false)
        }
        return null
    }


    protected fun <T : ViewModel> getViewModel(clazz: Class<T>): ViewModel = ViewModelProviders.of(this, viewModelFactory).get(clazz)

}