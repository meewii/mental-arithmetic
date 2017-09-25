package com.meewii.mentalarithmetic.ui.fragments

import android.os.Bundle
import android.view.View
import com.meewii.mentalarithmetic.dagger.components.ActivityComponent
import com.meewii.mentalarithmetic.presenters.AdditionsPresenter
import kotlinx.android.synthetic.main.fragment_operation.*
import javax.inject.Inject

class AdditionsFragment : BaseFragment() {

    @Inject
    lateinit var presenter: AdditionsPresenter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init presenter
        presenter
                .attachView(this)
                .newGame()

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
        super.newOperation()
        presenter.generateOperation()
    }

    override fun updateList() {
        operationAdapter.notifyDataSetChanged()

        val pos: Int = presenter.operationList.size
        recyclerView.scrollToPosition(pos - 1)
    }
}