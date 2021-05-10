package com.example.medicineadvisor.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {

    val noInternetMsg ="No Internet Connection!!"

    fun hideKeyboard(ctx : Context,v :View){
        var imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken,0)
    }

}