package com.example.plantsbabysitter.domain.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.plantsbabysitter.util.DataResources
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.*


class FirebaseQueryLiveData : LiveData<DataResources<DataSnapshot>> {
    private val query: Query
    private val listener =
        MyValueEventListener()

    constructor(query: Query) {
        this.query = query
    }

    constructor(ref: DatabaseReference) {
        query = ref
    }

    override fun onActive() {
        Log.d(LOG_TAG, "onActive")
        query.addValueEventListener(listener)
    }

    override fun onInactive() {
        Log.d(LOG_TAG, "onInactive")
        query.removeEventListener(listener)
    }

    private inner class MyValueEventListener : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = DataResources.Success(dataSnapshot)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e(
                LOG_TAG,
                "Can't listen to query $query",
                databaseError.toException()
            )
            FirebaseCrashlytics.getInstance().recordException(databaseError.toException())
            value = DataResources.Failure(databaseError.toException())
        }
    }

    companion object {
        private const val LOG_TAG = "FirebaseQueryLiveData"
    }
}