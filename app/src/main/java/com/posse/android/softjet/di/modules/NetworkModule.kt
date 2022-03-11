package com.posse.android.softjet.di.modules

import com.posse.android.softjet.utils.AndroidNetworkStatus
import com.posse.android.softjet.utils.NetworkStatus
import dagger.Binds
import dagger.Module

@Module
interface NetworkModule {

    @Suppress("FunctionName")
    @Binds
    fun bindAndroidNetworkStatus_to_NetworkStatus(androidNetworkStatus: AndroidNetworkStatus): NetworkStatus
}