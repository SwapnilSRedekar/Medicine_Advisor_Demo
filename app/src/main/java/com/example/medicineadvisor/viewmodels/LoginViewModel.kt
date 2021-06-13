package com.example.medicineadvisor.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var userName : MutableLiveData<String> = MutableLiveData()
    var userNameError : MutableLiveData<String> = MutableLiveData("")

    var userDob : MutableLiveData<String> = MutableLiveData("")
    var userDobError : MutableLiveData<String> = MutableLiveData("")

    var userGender : MutableLiveData<String> = MutableLiveData("")
    var userGenderError : MutableLiveData<String> = MutableLiveData("")

    var startActivity : MutableLiveData<Boolean> = MutableLiveData()


    fun checkMandatoryFields() {

        var flag = true
        if (userName.value.isNullOrEmpty()){
            userNameError.postValue("Enter your name")
            flag = false
        }else{
            userNameError.postValue("")
        }
        if (userDob.value.isNullOrEmpty()){
            userDobError.postValue("Select DOB")
            flag = false
        }else{
            userDobError.postValue("")
        }
        if (userGender.value.isNullOrEmpty() || userGender.value.equals("Gender")){
            userGenderError.postValue("Select Gender")
            flag = false
        }else{
            userGenderError.postValue("")
        }

        startActivity.postValue(flag)

    }


}