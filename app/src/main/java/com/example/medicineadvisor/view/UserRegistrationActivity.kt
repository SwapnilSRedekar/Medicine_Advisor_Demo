package com.example.medicineadvisor.view

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import com.example.medicineadvisor.R
import kotlinx.android.synthetic.main.activity_user_registration.*
import java.time.LocalDate
import java.time.Period
import java.util.*
import kotlin.collections.ArrayList

class UserRegistrationActivity : AppCompatActivity() {

    lateinit var userName : EditText
    lateinit var userGender : Spinner
    lateinit var userAge : TextView
    lateinit var calendarIcon : ImageView
    lateinit var userRegister : Button
    var name :String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        userName = findViewById(R.id.et_userName)
        userAge = findViewById(R.id.tv_dob)
        calendarIcon = findViewById(R.id.iv_calendarIcon)
        userRegister = findViewById(R.id.btn_registrUser)
        setGenderSpinner()

        var shareP = getSharedPreferences("SHARE_P",Context.MODE_PRIVATE)
        name = shareP.getString("Usr_NAME",null)

        calendarIcon.setOnClickListener {
            var now = Calendar.getInstance()
            var year = now.get(Calendar.YEAR)
            var months = now.get(Calendar.MONTH)
            var date = now.get(Calendar.DAY_OF_MONTH)
            var selectedDate :String? = null

           var cal = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                var usersAge = calculateAge(year,month+1,dayOfMonth)
               if (usersAge>=18){
                   userAge.setText(""+dayOfMonth+"/"+(month+1)+"/"+year)
                  }else{
                   userAge.setText("")
                   Toast.makeText(this,"Sorry!! You are under 18",Toast.LENGTH_SHORT).show()
               }

           }
           ,year,months,date)
            cal.show()

        }

        userRegister.setOnClickListener {
            var empty : Boolean = false

            if (userName.text.isEmpty()){
                userName.error = "Name cannot be blank!!"
                empty = true
            }
            if (spn_gender.selectedItem.equals("Gender")){
                (spn_gender.selectedView as TextView).error = "Gender can not be blank!!"
                empty = true
            }
            if (userAge.text.isEmpty()){
                (userAge).error = "Please select DOB"
                empty = true
            }
            if (empty) return@setOnClickListener

            var sharePrEdit = shareP.edit()
            sharePrEdit.putString("Usr_NAME",userName.text.toString())
            sharePrEdit.apply()

            var intent = Intent(this,UserActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    fun calculateAge(y :Int,m:Int,d:Int) : Int{
        var todaysDate = LocalDate.now()
        var selectedDate = LocalDate.of(y,m,d)
        var diff = Period.between(selectedDate,todaysDate)
        var age = diff.years
        return age
    }

    fun setGenderSpinner(){
        userGender = findViewById(R.id.spn_gender)
        userGender.adapter = setGenderAdapter(resources.getStringArray(R.array.gender))

        userGender.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                var selectedText = adapterView?.getChildAt(0) as TextView


                if (adapterView.getItemAtPosition(position).toString() == "Gender") {
                    selectedText.setTextColor(resources.getColor(R.color.colorWhite))
                } else {
                    selectedText.setTextColor(resources.getColor(R.color.colorParrotGreen))
                }
            }
        }
    }

    fun setGenderAdapter(spinnerType : Array<String>) : ArrayAdapter<String>{

        var adapter = object :ArrayAdapter<String>(this,R.layout.spinner_text_layout,spinnerType){

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val dropdownView = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    dropdownView.setTextColor(resources.getColor(R.color.colorWhite))

                } else {
                    dropdownView.setTextColor(resources.getColor(R.color.colorBlack))
                }
                return dropdownView
            }
        }
        return adapter
    }

    override fun onResume() {
        super.onResume()
        if (name!=null){
            var intent = Intent(this,UserActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}