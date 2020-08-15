package com.example.plantsbabysitter.domain.repository

import com.example.plantsbabysitter.util.DataResources
import com.google.firebase.crashlytics.FirebaseCrashlytics

interface IRepository {
    suspend fun getData(): DataResources<Int>

    fun recordNonFatalException(e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(e)
    }
}