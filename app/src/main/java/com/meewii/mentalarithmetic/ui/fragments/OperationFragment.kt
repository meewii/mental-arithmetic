package com.meewii.mentalarithmetic.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import com.meewii.mentalarithmetic.core.Const
import com.meewii.mentalarithmetic.dagger.components.ActivityComponent
import com.meewii.mentalarithmetic.models.Difficulty
import com.meewii.mentalarithmetic.models.Operator
import com.meewii.mentalarithmetic.presenters.OperationPresenter
import kotlinx.android.synthetic.main.fragment_operation.*
import javax.inject.Inject


class OperationFragment : BaseFragment() {

    @Inject
    lateinit var presenter: OperationPresenter

    lateinit var difficulty: Difficulty
    lateinit var operator: Operator

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            val diff = bundle.getString(Const.DIFFICULTY_EXTRA)
            val oper = bundle.getString(Const.OPERATOR_TYPE_EXTRA)
            Log.i("OperationFragment", "Operator: $oper - Difficulty: $diff")
            difficulty = Difficulty.valueOf(diff)
            operator = Operator.valueOf(oper)
            Log.i("OperationFragment", "Operator: $operator - Difficulty: $difficulty")

        } else {
            throw NullPointerException("No arguments were find.")
        }

        // Init presenter
        presenter
                .init(difficulty, operator)
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