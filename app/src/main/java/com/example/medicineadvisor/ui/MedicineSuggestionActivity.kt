package com.example.medicineadvisor.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicineadvisor.MedicinePost
import com.example.medicineadvisor.R
import com.example.medicineadvisor.adapter.MedicineAdapter
import com.example.medicineadvisor.adapter.RowClickListner
import com.example.medicineadvisor.databinding.ActivityMedicineSuggestionBinding
import com.example.medicineadvisor.db.MedicineEntity
import com.example.medicineadvisor.repositories.MedicineRepository
import com.example.medicineadvisor.utils.Utils
import com.example.medicineadvisor.viewmodels.MedicineViewmodel
import com.example.medicineadvisor.viewmodels.MedicineViewmodelFactory


class MedicineSuggestionActivity : AppCompatActivity(),RowClickListner{

    lateinit var medicineViewmodel: MedicineViewmodel
    lateinit var medicineAdapter: MedicineAdapter
    lateinit var medicineSuggestionBinding: ActivityMedicineSuggestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        medicineSuggestionBinding = DataBindingUtil.setContentView(this,R.layout.activity_medicine_suggestion)

        val medicineRepository = MedicineRepository(this)
        val medicineViewmodelFactory = MedicineViewmodelFactory(application,medicineRepository)
        medicineViewmodel = ViewModelProvider(this,medicineViewmodelFactory).get(MedicineViewmodel::class.java)

        medicineSuggestionBinding.btnMedSuggstnAdd.setOnClickListener {
            var medPost = MedicinePost()
            medPost.medicinePost(this, medicineViewmodel)

        }

        medicineSuggestionBinding.btnFetchMedicines.setOnClickListener {

            Utils.hideKeyboard(this,it)
            medicineAdapter = MedicineAdapter(this)
            medicineSuggestionBinding.rcyMedicineList.layoutManager = LinearLayoutManager(this)
            medicineSuggestionBinding.rcyMedicineList.adapter = medicineAdapter

            medicineViewmodel.getMedicines(if (medicineSuggestionBinding.etUserDisease.text.isEmpty()) null else medicineSuggestionBinding.etUserDisease.text.toString())
                ?.observe(this, Observer { medicineList ->
                    medicineAdapter.setMedicineList(ArrayList(medicineList))
                    medicineAdapter.notifyDataSetChanged()
                })
        }
    }


    override fun onItemClick(medList: MedicineEntity) {

        var intent = Intent(this,MedicineDetailsActivity::class.java)
        intent.putExtra("MED_NAME",medList.med_name)
        intent.putExtra("MED_USE",medList.usage)
        intent.putExtra("MED_DESC",medList.description)
        startActivity(intent)
    }


    override fun onDeleteClick(medList: MedicineEntity) {

        var alrtDlg = AlertDialog.Builder(this)
        alrtDlg.setTitle("Remove Medicine?")
        alrtDlg.setMessage(medList.med_name)

        alrtDlg.setPositiveButton("Yes",{ dialogInterface: DialogInterface, i: Int ->
            medicineViewmodel.deleteMedicine(medList.med_name)
        })
        alrtDlg.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
        alrtDlg.show()
    }

}