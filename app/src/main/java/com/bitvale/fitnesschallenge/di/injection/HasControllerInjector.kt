package com.bitvale.fitnesschallenge.di.injection

import com.bluelinelabs.conductor.Controller
import dagger.android.AndroidInjector


/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
interface HasControllerInjector {
    fun controllerInjector(): AndroidInjector<Controller>
}