package com.example.plantsbabysitter.domain.usecases

import com.example.plantsbabysitter.domain.repository.IRepository
import com.example.plantsbabysitter.util.DataResources

class UseCaseImpl (private val repository: IRepository): IUseCase {
    override suspend fun getData(): DataResources<Int> = repository.getData()
}