package com.posse.android.softjet.di.component

import android.app.Application
import com.posse.android.softjet.app.App
import com.posse.android.softjet.di.modules.ActivityModule
import com.posse.android.softjet.di.modules.AppModule
import com.posse.android.softjet.di.modules.DataSourceModule
import com.posse.android.softjet.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ActivityModule::class,
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        DataSourceModule::class,
        NetworkModule::class
    ]
)

@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}