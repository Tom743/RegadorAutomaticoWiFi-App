package com.example.plantsbabysitter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantsbabysitter.domain.usecases.IUseCase

class MainViewModelFactory(private val useCase: IUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IUseCase::class.java).newInstance(useCase)
    }
}