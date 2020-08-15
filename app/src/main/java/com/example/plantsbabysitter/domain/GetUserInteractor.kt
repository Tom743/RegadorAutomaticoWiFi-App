package com.example.plantsbabysitter.domain

/**
 * Gets the data from the user side
 *
 * @property db the database from where to get data
 */

class GetUserInteractor(private val db: Database) {
    suspend fun getData(): Any?{

        var data: Any? = null

        db.getData(TODO(), object: Database.CallBack{
            override fun onResultSuccess(value: Any?) {
                TODO("Not yet implemented")
            }

            override fun onResultFailure(value: String) {
                TODO("Not yet implemented")
            }
        })

    }
}