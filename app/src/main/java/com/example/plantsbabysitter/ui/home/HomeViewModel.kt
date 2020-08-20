package com.example.plantsbabysitter.ui.home

import androidx.lifecycle.ViewModel
import com.example.plantsbabysitter.domain.repo.FirebaseQueryLiveData
import com.example.plantsbabysitter.domain.repo.FirebaseWriteData
import com.google.firebase.database.FirebaseDatabase


class HomeViewModel: ViewModel() {

    private fun getLiveData(path: String): FirebaseQueryLiveData {
        val ref = FirebaseDatabase.getInstance().getReference(path).ref
        return FirebaseQueryLiveData(ref)
    }

    // TODO 20/AUG/2020 Move strings to resource file
    fun getPruebaLiveData(): FirebaseQueryLiveData {
        return getLiveData("prueba")
    }

    fun requestPlantData() {
        // Asks robot to update the data in the database
        FirebaseWriteData.setValue("request fresh data", true)
    }

    fun waterPlant() {
        // Asks robot to water the plantita1
        FirebaseWriteData.setValue("/plants/plantita1/water now", true)
    }
}