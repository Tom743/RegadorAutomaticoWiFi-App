package com.example.plantsbabysitter.domain.usecases

import com.example.plantsbabysitter.util.DataResources

interface IUseCase {
    suspend fun getData(): DataResources<Int>
}