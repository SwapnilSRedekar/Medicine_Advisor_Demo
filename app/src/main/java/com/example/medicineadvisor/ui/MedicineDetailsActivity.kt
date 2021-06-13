package com.example.medicineadvisor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.medicineadvisor.R
import com.example.medicineadvisor.databinding.ActivityMedicineDetailsBinding

class MedicineDetailsActivity : AppCompatActivity() {

    lateinit var activityMedicineDetailsBinding: ActivityMedicineDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_details)
        activityMedicineDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_medicine_details)

        activityMedicineDetailsBinding.tvMedNameDtls.text = "Name - "+intent.getStringExtra("MED_NAME")
        activityMedicineDetailsBinding.tvMedUsgDtls.text = "Usage - "+intent.getStringExtra("MED_USE")
        activityMedicineDetailsBinding.tvMedDescDtls.text = "Desc- "+intent.getStringExtra("MED_DESC")
    }
}