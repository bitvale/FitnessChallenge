package com.bitvale.fitnesschallenge.ui.reminder

import android.view.View
import com.bitvale.fitnesschallenge.R
import com.bitvale.fitnesschallenge.base.BaseController
import com.bitvale.fitnesschallenge.ui.home.HomeAdapter
import com.bitvale.fitnesschallenge.ui.home.mvp.HomeContract
import com.bitvale.fitnesschallenge.ui.home.mvp.HomePresenter
import com.bitvale.fitnesschallenge.ui.home.mvp.HomeViewState
import javax.inject.Inject
import javax.inject.Provider


/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 19-Feb-19
 */
class ReminderController : BaseController<HomeContract.View, HomeContract.Presenter, HomeViewState>(), HomeContract.View {

    override fun setAdapter(adapter: HomeAdapter) {
    }

    @Inject
    lateinit var presenterProvider: Provider<HomePresenter>

    override fun createViewState(): HomeViewState = HomeViewState()

    override fun createPresenter(): HomeContract.Presenter = presenterProvider.get()

    override fun getLayoutId(): Int = R.layout.view_reminder

    override fun onViewCreated(view: View) {

    }

    override fun animateOut() {

    }
}