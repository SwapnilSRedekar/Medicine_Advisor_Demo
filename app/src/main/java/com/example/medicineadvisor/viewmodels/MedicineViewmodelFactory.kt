package com.example.medicineadvisor.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medicineadvisor.repositories.MedicineRepository

class MedicineViewmodelFactory(val application: Application,val repository: MedicineRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MedicineViewmodel(application,repository) as T
    }
}