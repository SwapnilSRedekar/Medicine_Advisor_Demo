package com.example.medicineadvisor

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.medicineadvisor.databinding.AddMedicineLayoutBinding
import com.example.medicineadvisor.model.MedicineFields
import com.example.medicineadvisor.utils.Utils
import com.example.medicineadvisor.viewmodels.MedicineViewmodel

class MedicinePost {

    lateinit var addMedicineView: AddMedicineLayoutBinding

    fun medicinePost(ctx: Context, medicineViewmodel: MedicineViewmodel) {

        addMedicineView = DataBindingUtil.inflate(LayoutInflater.from(ctx),R.layout.add_medicine_layout,null,false)

        var alrt: AlertDialog.Builder = AlertDialog.Builder(ctx)
        alrt.setView(addMedicineView.root)
        val alrtDlg = alrt.create()

        addMedicineView.btnAddMedicine.setOnClickListener {
            var check = false
            if (addMedicineView.etMedNameAdd.text.toString().isEmpty()) {
                addMedicineView.etMedNameAdd.error = "Add Medicine Name"
                check = true
            }
            if (addMedicineView.etMedUsageAdd.text.toString().isEmpty()) {
                addMedicineView.etMedUsageAdd.error = "Add Medicine Usage"
                check = true
            }
            if (addMedicineView.etMedDescAdd.text.toString().isEmpty()) {
                addMedicineView.etMedDescAdd.error = "Add Medicine Description"
                check = true
            }
            if (check) return@setOnClickListener

            var newMedicine = MedicineFields(
                "",
                addMedicineView.etMedNameAdd.text.toString(),
                addMedicineView.etMedUsageAdd.text.toString(),
                addMedicineView.etMedDescAdd.text.toString()
            )

            var response = medicineViewmodel.addMedicine(newMedicine)
            if (response) {
                Toast.makeText(ctx, "Medicine added", Toast.LENGTH_LONG).show()
                Utils.hideKeyboard(ctx, it)
                alrtDlg.dismiss()

            } else {
                Toast.makeText(ctx, Utils.noInternetMsg, Toast.LENGTH_LONG).show()
                Utils.hideKeyboard(ctx, it)
            }
        }

        addMedicineView.btnCancelMedicine.setOnClickListener {
            Utils.hideKeyboard(ctx, it)
            alrtDlg.cancel()

        }
        alrtDlg.show()
    }
}