package com.example.medicineadvisor.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicineDao {

    @Query("SELECT * FROM MedicineListInfo where :use is null OR usage=:use")
    fun getAllMedicinesDB(use : String?) : LiveData<List<MedicineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMedicineDB(medName : MedicineEntity)

    @Delete
    fun deleteMedicineDB(medName: MedicineEntity)

    @Query("DELETE FROM MedicineListInfo WHERE med_name = :medN")
    fun deleteSpecidicMedicineDB(medN: String)

    @Query("SELECT * FROM MedicineListInfo where med_name = :medN and usage = :medUsa")
    fun medicineExist(medN : String,medUsa : String) : Int

    @Query("DELETE FROM MedicineListInfo")
    fun deleteAllMedicinesDB()

}