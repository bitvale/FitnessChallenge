package com.bitvale.fitnesschallenge.base.mvp

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
open class BasePresenter<V : MvpView> : MvpBasePresenter<V>() {
    protected val disposables: CompositeDisposable = CompositeDisposable()

    override fun destroy() {
        disposables.clear()
    }
}