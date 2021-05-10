package com.example.medicineadvisor.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicineadvisor.MedicinePost
import com.example.medicineadvisor.R
import com.example.medicineadvisor.adapter.MedicineAdapter
import com.example.medicineadvisor.adapter.RowClickListner
import com.example.medicineadvisor.db.MedicineEntity
import com.example.medicineadvisor.repositories.MedicineRepository
import com.example.medicineadvisor.utils.Utils
import com.example.medicineadvisor.viewmodels.MedicineViewmodel
import com.example.medicineadvisor.viewmodels.MedicineViewmodelFactory


class MedicineSuggestionActivity : AppCompatActivity(),RowClickListner{

    lateinit var medAdd: Button
    lateinit var enterDisease: EditText
    lateinit var fetchMed: Button
    lateinit var recyMedi: RecyclerView
    lateinit var medicineViewmodel: MedicineViewmodel
    lateinit var ad : MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_medicine_suggestion)

        medAdd = findViewById(R.id.btn_medSuggstnAdd)
        enterDisease = findViewById(R.id.et_userDisease)
        fetchMed = findViewById(R.id.btn_fetchMedicines)
        recyMedi = findViewById(R.id.rcy_medicineList)

        val medicineRepository = MedicineRepository(this)
        val medicineViewmodelFactory = MedicineViewmodelFactory(application,medicineRepository)
        medicineViewmodel = ViewModelProvider(this,medicineViewmodelFactory).get(MedicineViewmodel::class.java)

        medAdd.setOnClickListener {
            var medPost = MedicinePost()
            medPost.medicinePost(this, medicineViewmodel)

        }

        fetchMed.setOnClickListener {

            Utils.hideKeyboard(this,it)

            ad = MedicineAdapter(this)
            recyMedi.layoutManager = LinearLayoutManager(this)
            recyMedi.adapter = ad

            medicineViewmodel.getMedicines(if (enterDisease.text.isEmpty()) null else enterDisease.text.toString())
                ?.observe(this, Observer { medicineList ->
                    ad.setMedicineList(ArrayList(medicineList))
                    ad.notifyDataSetChanged()
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