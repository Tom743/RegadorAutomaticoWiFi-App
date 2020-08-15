package com.example.plantsbabysitter.util

sealed class DataResources<out T> {
    class Loading<out T>: DataResources<T>()
    data class Success<out T>(val data: T): DataResources<T>()
    data class Failure<out T>(val exception: Exception): DataResources<T>()
}