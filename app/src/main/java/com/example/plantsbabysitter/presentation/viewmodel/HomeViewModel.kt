package com.example.plantsbabysitter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.plantsbabysitter.domain.usecases.IUseCase
import com.example.plantsbabysitter.util.DataResources
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val useCase: IUseCase): ViewModel() {

    val fetchData = liveData(Dispatchers.IO) {
        emit(DataResources.Loading())
        try {
            val data = useCase.getData()
            emit(data)
        } catch (e: Exception) {
            emit(DataResources.Failure(e))
        }
    }
}