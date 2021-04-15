package com.example.medicineadvisor.model

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.medicineadvisor.R
import com.example.medicineadvisor.retrofit.MedicineInterface
import com.example.medicineadvisor.retrofit.MedicineServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicinePost {

    fun medicinePost(ctx: Context) {
        val addMedicineView = LayoutInflater.from(ctx).inflate(R.layout.add_medicine_layout, null)

        var medicineName = addMedicineView.findViewById<EditText>(R.id.et_medNameAdd)
        var medicineUsage = addMedicineView.findViewById<EditText>(R.id.et_medUsageAdd)
        var medicineDesc = addMedicineView.findViewById<EditText>(R.id.et_medDescAdd)
        var medicineAddBtn = addMedicineView.findViewById<Button>(R.id.btn_addMedicine)
        var medicineCanclBtn = addMedicineView.findViewById<Button>(R.id.btn_cancelMedicine)


        var alrt: AlertDialog.Builder = AlertDialog.Builder(ctx)
        alrt.setView(addMedicineView)
        val alrtDlg = alrt.create()

        medicineAddBtn.setOnClickListener {
            var check = false
            if (medicineName.text.toString().isEmpty()) {
                medicineName.error = "Add Medicine Name"
                check = true
            }
            if (medicineUsage.text.toString().isEmpty()) {
                medicineUsage.error = "Add Medicine Usage"
                check = true
            }
            if (medicineDesc.text.toString().isEmpty()) {
                medicineDesc.error = "Add Medicine Description"
                check = true
            }
            if (check) return@setOnClickListener

            var api = MedicineServiceBuilder().getBuild(MedicineInterface::class.java)

            var fields = Fields()
            fields.addMedicine = ""
            fields.medName = medicineName.text.toString()
            fields.medUsage = medicineUsage.text.toString()
            fields.medDescription = medicineDesc.text.toString()

            var res = api.addMedicine(
                MedicineFields(
                    fields.addMedicine,
                    fields.medName,
                    fields.medUsage,
                    fields.medDescription
                )
            )
            res.enqueue(object : Callback<MedicineFields> {
                override fun onFailure(call: Call<MedicineFields>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<MedicineFields>,
                    response: Response<MedicineFields>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            ctx, "Medicine" +
                                    " Added in list", Toast.LENGTH_SHORT
                        ).show()
                    }
                    alrtDlg.dismiss()
                }

            })

        }

        medicineCanclBtn.setOnClickListener {
            alrtDlg.dismiss()
        }
        alrtDlg.show()
    }
}
