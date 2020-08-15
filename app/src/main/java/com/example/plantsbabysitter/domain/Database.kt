package com.example.plantsbabysitter.domain

import javax.security.auth.callback.Callback


/**
 * Database
 *
 * Gets data from the real database
 */
interface Database {

    interface CallBack {
        fun onResultSuccess(value: Any?)
        fun onResultFailure(value: String)
    }

    suspend fun getData(dataId: String, listener: CallBack)

}