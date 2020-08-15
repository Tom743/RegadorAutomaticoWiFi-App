package com.example.plantsbabysitter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.plantsbabysitter.domain.repo.FirebaseQueryLiveData
import com.google.firebase.database.FirebaseDatabase


class HomeViewModel: ViewModel() {

    private fun getLiveData(path: String): FirebaseQueryLiveData {
        val ref = FirebaseDatabase.getInstance().getReference(path).ref
        return FirebaseQueryLiveData(ref)
    }

    fun getPruebaLiveData(): FirebaseQueryLiveData {
        return getLiveData("prueba")
    }

}