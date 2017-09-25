package com.meewii.mentalarithmetic.ui.fragments

import android.os.Bundle
import android.view.View
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
                .attachView(this)
                .generateOperation()

        // set up list
        setUpAdapter(presenter.operationList)

        // set listener on button
        submitButton.setOnClickListener {
            presenter.onSubmitSolution(solutionInput.text)
        }
    }

    override fun inject(component: ActivityComponent) {
        component.inject(this)
    }

    override fun newOperation() {
        solutionInput.setText("")
        presenter.generateOperation()
    }

    override fun updateList() {
        operationAdapter.notifyDataSetChanged()

        val pos: Int = presenter.operationList.size
        recyclerView.scrollToPosition(pos - 1)

        // reset current operation
        newOperation()
    }
}