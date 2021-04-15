package com.example.medicineadvisor.view

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer.create
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicineadvisor.R
import com.example.medicineadvisor.adapter.MedicineAdapter
import com.example.medicineadvisor.adapter.RowClickListner
import com.example.medicineadvisor.model.MedicineList
import com.example.medicineadvisor.model.MedicinePost
import com.example.medicineadvisor.retrofit.MedicineInterface
import com.example.medicineadvisor.retrofit.MedicineServiceBuilder
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MedicineSuggestionActivity : AppCompatActivity(),RowClickListner {

    lateinit var medAdd: Button
    lateinit var enterDisease: EditText
    lateinit var fetchMed: Button
    lateinit var recyMedi: RecyclerView
    lateinit var lists : MutableList<MedicineList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_suggestion)

        medAdd = findViewById(R.id.btn_medSuggstnAdd)
        enterDisease = findViewById(R.id.et_userDisease)
        fetchMed = findViewById(R.id.btn_fetchMedicines)
        recyMedi = findViewById(R.id.rcy_medicineList)

        medAdd.setOnClickListener{

            var medPost = MedicinePost()
            medPost.medicinePost(this)
        }

        fetchMed.setOnClickListener {

            var retrofit = MedicineServiceBuilder().getBuild(MedicineInterface::class.java)

            var res =
                retrofit.getMedicines(if (enterDisease.text.isEmpty()) null else enterDisease.text.toString())

            res.enqueue(object : Callback<MutableList<MedicineList>> {
                override fun onFailure(call: Call<MutableList<MedicineList>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<MutableList<MedicineList>>,
                    response: Response<MutableList<MedicineList>>
                ) {
                    if (response.isSuccessful) {
                         lists = response.body()!!
                        recyMedi.layoutManager =
                            LinearLayoutManager(this@MedicineSuggestionActivity)
                        recyMedi.adapter = MedicineAdapter(this@MedicineSuggestionActivity,this@MedicineSuggestionActivity, lists)


                    }
                }
            })
        }
    }

    override fun onItemClick(med: MedicineList) {

        var intent = Intent(this,MedicineDetailsActivity::class.java)
        intent.putExtra("MED_NAME",med.fields.med_name)
        intent.putExtra("MED_USE",med.fields.usage)
        intent.putExtra("MED_DESC",med.fields.description)
        startActivity(intent)
    }


    override fun onDeleteClick(med: MedicineList,position: Int) {

        var alrtDlg = AlertDialog.Builder(this)
        alrtDlg.setTitle("Medicine Not Useful")
        alrtDlg.setMessage(med.fields.med_name)
        var pos = lists.get(position)

        alrtDlg.setPositiveButton("Not useful",{ dialogInterface: DialogInterface, i: Int ->
            var api = MedicineServiceBuilder().getBuild(MedicineInterface::class.java)
            var rs = api.deleteMedicine(med.fields.med_name)

            rs.enqueue(object :Callback<Unit>{
                override fun onFailure(call: Call<Unit>, t: Throwable) {

                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {

                    lists.removeAt(position)
                    recyMedi.layoutManager =
                        LinearLayoutManager(this@MedicineSuggestionActivity)
                    recyMedi.adapter = MedicineAdapter(this@MedicineSuggestionActivity,this@MedicineSuggestionActivity, lists)

                }
            })

        })
        alrtDlg.setNegativeButton("Useful",{ dialogInterface: DialogInterface, i: Int -> })

        alrtDlg.show()

    }

}