package com.bitvale.fitnesschallenge

import android.app.Application
import com.bitvale.fitnesschallenge.di.DaggerAppComponent
import com.bitvale.fitnesschallenge.di.injection.HasControllerInjector
import com.bluelinelabs.conductor.Controller
import dagger.android.DispatchingAndroidInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
class ChallengeApp : Application(), HasControllerInjector {

    @Inject
    lateinit var dispatchingControllerInjector: DispatchingAndroidInjector<Controller>

    override fun controllerInjector(): DispatchingAndroidInjector<Controller> {
        return dispatchingControllerInjector
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) { Timber.plant(Timber.DebugTree()) }
        DaggerAppComponent.builder().create(this).inject(this)
    }
}