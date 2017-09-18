package com.meewii.mentalarithmetic.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.meewii.mentalarithmetic.MainAdapter
import com.meewii.mentalarithmetic.dagger.components.ActivityComponent
import com.meewii.mentalarithmetic.presenters.SubtractionsPresenter
import kotlinx.android.synthetic.main.fragment_operation.*
import javax.inject.Inject

class SubtractionsFragment : BaseFragment() {

    @Inject
    lateinit var presenter: SubtractionsPresenter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init presenter
        presenter
                .init("Hello I'm injected with Dagger")
                .attachView(this)
                .generateOperation()

        // set up list
        mainAdapter = MainAdapter(activity.applicationContext, presenter.operationList)
        recyclerView.layoutManager = LinearLayoutManager(activity.applicationContext, LinearLayout.VERTICAL, false)
        recyclerView.adapter = mainAdapter

        // set listener on button
        submitButton.setOnClickListener {
            presenter.onSubmitSolution(solutionInput.text)
        }
    }

    override fun inject(component: ActivityComponent) {
        component.inject(this)
    }

    override fun resetCalculator() {
        solutionInput.setText("")
        presenter.generateOperation()
    }

    override fun updateList() {
        mainAdapter.notifyDataSetChanged()

        val pos: Int = presenter.operationList.size
        recyclerView.scrollToPosition(pos - 1)

        // reset current operation
        resetCalculator()
    }
}