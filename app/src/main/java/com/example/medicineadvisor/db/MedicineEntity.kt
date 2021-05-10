package com.example.medicineadvisor.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MedicineListInfo")
data class MedicineEntity(
    var med_name: String,
    var usage: String,
    var description: String
){
    @PrimaryKey(autoGenerate = true)
    var pk : Int = 0
}