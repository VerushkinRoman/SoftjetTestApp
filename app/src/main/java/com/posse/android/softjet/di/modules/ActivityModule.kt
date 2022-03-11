package com.posse.android.softjet.di.modules

import com.posse.android.softjet.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FragmentModule::class])
interface ActivityModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity
}