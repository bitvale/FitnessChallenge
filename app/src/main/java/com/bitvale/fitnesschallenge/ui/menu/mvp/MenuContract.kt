package com.bitvale.fitnesschallenge.ui.menu.mvp

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
class MenuContract {
    interface View : MvpView

    interface Presenter : MvpPresenter<View>
}