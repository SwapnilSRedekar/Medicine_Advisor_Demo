package com.example.medicineadvisor.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.medicineadvisor.db.MedicineEntity
import com.example.medicineadvisor.model.MedicineFields
import com.example.medicineadvisor.model.MedicineList
import com.example.medicineadvisor.repositories.MedicineRepository
import com.example.medicineadvisor.utils.Utils

class MedicineViewmodel(application: Application,var repo : MedicineRepository) : AndroidViewModel(application) {


    fun getMedicines(use:String?): LiveData<List<MedicineEntity>>? {
        if (hasInternetConnection()){
            repo.getMedicinesRepo(use)
        }else{
            Toast.makeText(getApplication(), Utils.noInternetMsg, Toast.LENGTH_SHORT).show()
        }
        return repo.getMedicinesFromDB(use)
    }

    fun addMedicine(medicineFields: MedicineFields):Boolean{
        var flag = false
        if (hasInternetConnection()) {
            repo.addMedicine(medicineFields)
            flag = true
        } else {

        }
        return flag

    }

    fun deleteMedicine(med_name : String){
        if (hasInternetConnection()){
            repo.deleteMedicine(med_name)
        }else{
            Toast.makeText(getApplication(),Utils.noInternetMsg,Toast.LENGTH_LONG).show()
        }

    }

    fun hasInternetConnection(): Boolean {
        var connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }

        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE->true
                    else -> false
                }
            }
        }
        return false
    }

}