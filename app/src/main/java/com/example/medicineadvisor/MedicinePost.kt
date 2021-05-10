package com.example.medicineadvisor

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.medicineadvisor.model.MedicineFields
import com.example.medicineadvisor.utils.Utils
import com.example.medicineadvisor.viewmodels.MedicineViewmodel

class MedicinePost {

    fun medicinePost(ctx:Context,medicineViewmodel: MedicineViewmodel) {

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

            var newMedicine = MedicineFields(
                "", medicineName.text.toString(),
                medicineUsage.text.toString(), medicineDesc.text.toString()
            )

            var response = medicineViewmodel.addMedicine(newMedicine)
            if (response){
                Toast.makeText(ctx,"Medicine added",Toast.LENGTH_LONG).show()
                Utils.hideKeyboard(ctx,it)
                alrtDlg.dismiss()

            }else{
                Toast.makeText(ctx,Utils.noInternetMsg,Toast.LENGTH_LONG).show()
                Utils.hideKeyboard(ctx,it)
            }
        }

        medicineCanclBtn.setOnClickListener {
            Utils.hideKeyboard(ctx,it)
            alrtDlg.cancel()

        }
        alrtDlg.show()
    }
}