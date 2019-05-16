package com.bitvale.fitnesschallenge.di

import com.bitvale.fitnesschallenge.ui.home.HomeController
import com.bitvale.fitnesschallenge.ui.menu.MenuController
import com.bitvale.fitnesschallenge.ui.reminder.ReminderController
import com.bitvale.fitnesschallenge.ui.settings.SettingsController
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 22-Jan-19
 */
@Module
abstract class ControllersBuilder {

    @ContributesAndroidInjector
    abstract fun contributeHome(): HomeController

    @ContributesAndroidInjector
    abstract fun contributeMenu(): MenuController

    @ContributesAndroidInjector
    abstract fun contributeSettings(): SettingsController

    @ContributesAndroidInjector
    abstract fun contributeReminder(): ReminderController
}