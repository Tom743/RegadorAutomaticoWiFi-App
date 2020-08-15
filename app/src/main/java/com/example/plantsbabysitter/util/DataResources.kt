package com.example.plantsbabysitter.util

sealed class DataResources<out T> {
    // class Loading<out T>: DataResources<T>() TODO 25/AUG/2020 Use this when you first call the query live data to get the graphs or whatever
    data class Success<out T>(val data: T): DataResources<T>()
    data class Failure<out T>(val exception: Exception): DataResources<T>()
}