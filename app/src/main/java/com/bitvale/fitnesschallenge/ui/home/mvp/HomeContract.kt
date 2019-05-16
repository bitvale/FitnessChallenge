package com.bitvale.fitnesschallenge.ui.home.mvp

import com.bitvale.fitnesschallenge.ui.home.HomeAdapter
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
class HomeContract {
    interface View : MvpView {
        fun setAdapter(adapter: HomeAdapter)
    }

    interface Presenter : MvpPresenter<View>
}