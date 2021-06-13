package com.example.medicineadvisor.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medicineadvisor.R
import com.example.medicineadvisor.databinding.ActivityUserRegistrationBinding
import com.example.medicineadvisor.utils.FindAge
import com.example.medicineadvisor.utils.Utils
import com.example.medicineadvisor.viewmodels.LoginViewModel
import java.util.*

class UserRegistrationActivity : AppCompatActivity() {

    var name :String? = ""
    lateinit var binding: ActivityUserRegistrationBinding
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_registration)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
        binding.spnGender.inputType = InputType.TYPE_NULL
        binding.tvDob.inputType = InputType.TYPE_NULL

        setGenderSpinner()

        var shareP = getSharedPreferences("SHARE_P",Context.MODE_PRIVATE)
        name = shareP.getString("Usr_NAME",null)

        binding.tvDob.setOnClickListener {
            calendarOpen(it)
        }
        binding.tilDOB.setEndIconOnClickListener {
            calendarOpen(it)
        }

        loginViewModel.startActivity.observe(this,Observer{userActivity->
            if (userActivity) {
                var sharePrEdit = shareP.edit()
                sharePrEdit.putString("Usr_NAME", binding.etUserName.text.toString())
                sharePrEdit.apply()

                var intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    fun calendarOpen(view: View) {
        Utils.hideKeyboard(this,view)
        var now = Calendar.getInstance()
        var currentDay = now.get(Calendar.DAY_OF_MONTH)
        var currentMonth = now.get(Calendar.MONTH)
        var currentYear = now.get(Calendar.YEAR)

        var cal = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            var usersAge = FindAge.calculateAge(dayOfMonth,(month+1),year,currentDay,(currentMonth+1),currentYear)
            if (usersAge>=18){
                binding.tvDob.setText(""+dayOfMonth+"/"+(month+1)+"/"+year)
            }else{
                binding.tvDob.setText("")
                Toast.makeText(this,"Sorry!! You are under 18",Toast.LENGTH_SHORT).show()
            }
        }
            ,currentYear,currentMonth,currentDay)
        cal.show()
    }

    fun setGenderSpinner() {
        val genderList = resources.getStringArray(R.array.gender)
        var arrayAdapter = ArrayAdapter(this, R.layout.spinner_text_layout, genderList)
        binding.spnGender.setAdapter(arrayAdapter)
    }

    override fun onResume() {
        super.onResume()
        if (name != null) {
            var intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}