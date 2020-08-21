package com.example.plantsbabysitter.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.plantsbabysitter.R
import com.example.plantsbabysitter.domain.repo.FirebaseQueryLiveData
import com.example.plantsbabysitter.domain.repo.FirebaseWriteData
import com.google.firebase.database.FirebaseDatabase


class HomeViewModel: ViewModel() {

    private fun getLiveData(path: String): FirebaseQueryLiveData {
        val ref = FirebaseDatabase.getInstance().getReference(path).ref
        return FirebaseQueryLiveData(ref)
    }

    fun getPruebaLiveData(): FirebaseQueryLiveData {
        return getLiveData("prueba")
    }

    fun requestPlantData(context: Context) {
        // Asks robot to update the data in the database
        FirebaseWriteData.setValue(context.getString(R.string.refresh_data_now_path), true)
    }

    fun waterPlant(context: Context) {
        // Asks robot to water the plantita1
        FirebaseWriteData.setValue(context.getString(R.string.water_now_path), true)
    }
}