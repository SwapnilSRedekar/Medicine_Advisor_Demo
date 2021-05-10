package com.example.medicineadvisor.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medicineadvisor.db.MedicineDB
import com.example.medicineadvisor.db.MedicineEntity
import com.example.medicineadvisor.model.MedicineFields
import com.example.medicineadvisor.model.MedicineList
import com.example.medicineadvisor.retrofit.MedicineInterface
import com.example.medicineadvisor.retrofit.MedicineServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicineRepository(var ctx:Context) {

    fun getMedicinesRepo(use: String?) {
        var api = MedicineServiceBuilder().getBuild(MedicineInterface::class.java)
        val allLi = MutableLiveData<List<MedicineList>>()
        api.getMedicines(use).enqueue(object : Callback<List<MedicineList>> {
            override fun onFailure(call: Call<List<MedicineList>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<MedicineList>>,
                response: Response<List<MedicineList>>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    MedicineDB.getAppDatabase(ctx)?.getMedDao()?.deleteAllMedicinesDB()

                    var res = response.body()!!
                    allLi.postValue(res)
                    for (i in 0..res.size - 1) {
                        var medName = res.get(i).fields.med_name
                        var medUsage = res.get(i).fields.usage
                        var medDesc = res.get(i).fields.description
                        var med = MedicineEntity(medName, medUsage, medDesc)

                        if (MedicineDB.getAppDatabase(ctx)?.getMedDao()?.medicineExist(medName, medUsage) == 0) {
                            MedicineDB.getAppDatabase(ctx)?.getMedDao()?.addMedicineDB(med)
                        }

                    }
                }
            }
        })
    }

    fun deleteMedicine(med_name : String){
        var api = MedicineServiceBuilder().getBuild(MedicineInterface::class.java)
        api.deleteMedicine(med_name).enqueue(object : Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {

            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful){
                      MedicineDB.getAppDatabase(ctx)?.getMedDao()?.deleteSpecidicMedicineDB(med_name)
                }
            }
        })
    }

    fun getMedicinesFromDB(use:String?): LiveData<List<MedicineEntity>>? {
        var allMedicines = MedicineDB.getAppDatabase(ctx)?.getMedDao()?.getAllMedicinesDB(use)
        return allMedicines
    }

    fun addMedicine(medicineFields: MedicineFields) {
        var api = MedicineServiceBuilder().getBuild(MedicineInterface::class.java)

        api.addMedicine(medicineFields).enqueue(object : Callback<MedicineFields>{
            override fun onFailure(call: Call<MedicineFields>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<MedicineFields>,
                response: Response<MedicineFields>
            ) {
                if (response.isSuccessful){
                }
            }
        })

    }
}