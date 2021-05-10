package com.example.medicineadvisor.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.medicineadvisor.MedicinePost
import com.example.medicineadvisor.R
import com.example.medicineadvisor.repositories.MedicineRepository
import com.example.medicineadvisor.viewmodels.MedicineViewmodel
import com.example.medicineadvisor.viewmodels.MedicineViewmodelFactory

class UserActivity : AppCompatActivity() {

    lateinit var regUser : TextView
    lateinit var regUsrFlSts : TextView
    lateinit var regUserWell : Button
    lateinit var regUserNotWell : Button
    lateinit var regResetUser : Button
    lateinit var medicineViewmodel: MedicineViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        regUser = findViewById(R.id.tv_welcomeUser)
        regUsrFlSts = findViewById(R.id.tv_userFeelStatus)
        regUserWell = findViewById(R.id.btn_well)
        regUserNotWell = findViewById(R.id.btn_notWell)
        regResetUser = findViewById(R.id.btn_resetUser)

        val medicineRepository = MedicineRepository(this)
        val medicineViewmodelFactory = MedicineViewmodelFactory(application,medicineRepository)
        medicineViewmodel = ViewModelProvider(this,medicineViewmodelFactory).get(MedicineViewmodel::class.java)

        var shareP = getSharedPreferences("SHARE_P",Context.MODE_PRIVATE)
        var name = shareP.getString("Usr_NAME",null)

        regUser.setText("Welcome $name!!")
        regUsrFlSts.setText("Hey $name how are you feeling today??")

        regResetUser.setOnClickListener {
            var sharePrEdit = shareP.edit()
            sharePrEdit.clear()
            sharePrEdit.apply()


            var intent = Intent(this,UserRegistrationActivity::class.java)
            startActivity(intent)
            finish()

        }

        regUserNotWell.setOnClickListener {
            var intent = Intent(this,MedicineSuggestionActivity::class.java)
            startActivity(intent)
        }
        regUserWell.setOnClickListener {
            var altDl = AlertDialog.Builder(this)
            altDl.setTitle("Would you like to add medicine??")
            altDl.setPositiveButton("Yes",{ dialogInterface: DialogInterface, i: Int ->
                var medPost = MedicinePost()
                medPost.medicinePost(this,medicineViewmodel)
            })
            altDl.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int ->

            })
            altDl.show()
        }



    }
}