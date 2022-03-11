package com.posse.android.softjet.utils

import kotlinx.coroutines.flow.StateFlow

interface NetworkStatus {
    fun isOnline(): StateFlow<Boolean>
}