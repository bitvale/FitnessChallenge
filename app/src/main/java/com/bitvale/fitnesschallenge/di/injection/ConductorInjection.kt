package com.bitvale.fitnesschallenge.di.injection

import com.bluelinelabs.conductor.Controller
import dagger.internal.Preconditions
import timber.log.Timber


/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
object ConductorInjection {

    fun inject(controller: Controller) {
        Preconditions.checkNotNull(controller, "controller")
        val hasDispatchingControllerInjector = findHasControllerInjector(controller)
        Timber.d(
            "An injector for %s was found in %s", controller.javaClass.canonicalName,
            hasDispatchingControllerInjector.javaClass.canonicalName
        )
        val controllerInjector = hasDispatchingControllerInjector.controllerInjector()
        Preconditions.checkNotNull(
            controllerInjector, "%s.controllerInjector() returned null",
            hasDispatchingControllerInjector.javaClass.canonicalName
        )
        controllerInjector.inject(controller)
    }

    private fun findHasControllerInjector(controller: Controller): HasControllerInjector {
        val parentController: Controller? = controller

        do {
            if (parentController!!.parentController == null) {
                val activity = controller.activity
                if (activity is HasControllerInjector) {
                    return activity
                }

                if (activity!!.application is HasControllerInjector) {
                    return activity.application as HasControllerInjector
                }

                throw IllegalArgumentException(
                    String.format("No injector was found for %s", controller.javaClass.canonicalName)
                )
            }
        } while (parentController !is HasControllerInjector)

        return parentController
    }
}