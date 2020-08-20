package com.example.plantsbabysitter.domain.repo

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.FirebaseDatabase

object FirebaseWriteData {
    fun setValue(path: String, value: Any) {
        FirebaseDatabase.getInstance().getReference(path).setValue(value)
            .addOnFailureListener { exception ->
                Log.e(LOG_TAG, "Can't set value to $path", exception)
                FirebaseCrashlytics.getInstance().recordException(exception)
            }
    }

    private const val LOG_TAG = "FirebaseWriteData"
}