package com.meewii.mentalarithmetic.presenters

import android.content.Context
import android.util.Log

class AdditionsPresenter(context: Context) : OperationPresenter {

    override fun init(saySomething: String) {
        Log.d("Test", "AdditionsPresenter - init...")
    }

}