package com.bitvale.fitnesschallenge.ui.home.mvp

import com.bitvale.fitnesschallenge.base.mvp.BasePresenter
import com.bitvale.fitnesschallenge.ui.home.HomeAdapter
import com.bitvale.fitnesschallenge.model.MainItem
import javax.inject.Inject

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
class HomePresenter @Inject constructor() : BasePresenter<HomeContract.View>(),
    HomeContract.Presenter {

    private val adapter: HomeAdapter = HomeAdapter(MainItem.getItems())

    override fun attachView(view: HomeContract.View) {
        super.attachView(view)
        ifViewAttached {
            it.setAdapter(adapter)
        }
    }
}