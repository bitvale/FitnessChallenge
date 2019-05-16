package com.bitvale.fitnesschallenge.ui.menu.mvp

import com.bitvale.fitnesschallenge.base.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 18-Feb-19
 */
class MenuPresenter @Inject constructor() : BasePresenter<MenuContract.View>(),
    MenuContract.Presenter {

    override fun attachView(view: MenuContract.View) { }
}