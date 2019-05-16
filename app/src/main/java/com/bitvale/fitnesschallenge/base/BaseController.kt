package com.bitvale.fitnesschallenge.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.bitvale.fitnesschallenge.di.injection.ConductorInjection
import com.bluelinelabs.conductor.Controller
import com.hannesdorfmann.mosby3.conductor.viewstate.MvpViewStateController
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.*
import timber.log.Timber

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
abstract class BaseController<V : MvpView, P : MvpPresenter<V>, VS : ViewState<V>>: MvpViewStateController<V, P, VS>(),
    LayoutContainer {

    init {
        addLifecycleListener(object : LifecycleListener() {

            override fun postCreateView(controller: Controller, view: View) {
                onViewCreated(view)
            }
        })
    }

    override val containerView: View?
        get() = view

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        ConductorInjection.inject(this)
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        clearFindViewByIdCache()
    }

    open fun onViewCreated(view: View) {}

    abstract override fun createViewState(): VS

    abstract override fun createPresenter(): P

    override fun onViewStateInstanceRestored(instanceStateRetained: Boolean) {
        //no-op
    }

    override fun onNewViewStateInstance() {
        //no-op
    }

    protected fun getNavigator(): Navigator = activity as Navigator

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    abstract fun animateOut()
}