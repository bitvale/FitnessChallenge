package com.bitvale.fitnesschallenge.di

import com.bitvale.fitnesschallenge.ChallengeApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, ControllersBuilder::class])
interface AppComponent : AndroidInjector<ChallengeApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<ChallengeApp>()
}