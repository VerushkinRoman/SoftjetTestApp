package com.posse.android.softjet.di.modules

import com.posse.android.softjet.view.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
interface FragmentModule {

    @ContributesAndroidInjector
    fun contributeMainFragment(): MainFragment
}