package com.example.medicineadvisor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.medicineadvisor.R

class MedicineDetailsActivity : AppCompatActivity() {

    lateinit var medNamDetails : TextView
    lateinit var medUsgDetails : TextView
    lateinit var medDesDetails : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_details)

        medNamDetails = findViewById(R.id.tv_medNameDtls)
        medUsgDetails = findViewById(R.id.tv_medUsgDtls)
        medDesDetails = findViewById(R.id.tv_medDescDtls)

        medNamDetails.text = "Name - "+intent.getStringExtra("MED_NAME")
        medUsgDetails.text = "Usage - "+intent.getStringExtra("MED_USE")
        medDesDetails.text = "Desc- "+intent.getStringExtra("MED_DESC")

    }
}