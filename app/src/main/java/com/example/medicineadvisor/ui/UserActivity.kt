package com.example.medicineadvisor.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.medicineadvisor.MedicinePost
import com.example.medicineadvisor.R
import com.example.medicineadvisor.databinding.ActivityUserBinding
import com.example.medicineadvisor.repositories.MedicineRepository
import com.example.medicineadvisor.viewmodels.MedicineViewmodel
import com.example.medicineadvisor.viewmodels.MedicineViewmodelFactory

class UserActivity : AppCompatActivity() {

    lateinit var medicineViewmodel: MedicineViewmodel
    lateinit var activityUserBinding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUserBinding= DataBindingUtil.setContentView(this,R.layout.activity_user)

        val medicineRepository = MedicineRepository(this)
        val medicineViewmodelFactory = MedicineViewmodelFactory(application,medicineRepository)
        medicineViewmodel = ViewModelProvider(this,medicineViewmodelFactory).get(MedicineViewmodel::class.java)

        var shareP = getSharedPreferences("SHARE_P",Context.MODE_PRIVATE)
        var name = shareP.getString("Usr_NAME",null)

        activityUserBinding.tvWelcomeUser.setText("Welcome $name!!")
        activityUserBinding.tvUserFeelStatus.setText("Hey $name how are you feeling today??")

        activityUserBinding.btnResetUser.setOnClickListener {
            var sharePrEdit = shareP.edit()
            sharePrEdit.clear()
            sharePrEdit.apply()

            var intent = Intent(this,UserRegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        activityUserBinding.btnNotWell.setOnClickListener {
            var intent = Intent(this,MedicineSuggestionActivity::class.java)
            startActivity(intent)
        }
        activityUserBinding.btnWell.setOnClickListener {
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