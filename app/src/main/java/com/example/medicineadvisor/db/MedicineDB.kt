package com.example.medicineadvisor.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MedicineEntity::class],version = 1)
abstract class MedicineDB : RoomDatabase(){

    abstract fun getMedDao() : MedicineDao

    companion object{

        private var INSTANCE : MedicineDB?= null

        fun getAppDatabase(ctx: Context): MedicineDB? {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<MedicineDB>(ctx, MedicineDB::class.java, "AppDb")
                    .allowMainThreadQueries().build()
            }
            return INSTANCE
        }

    }

}