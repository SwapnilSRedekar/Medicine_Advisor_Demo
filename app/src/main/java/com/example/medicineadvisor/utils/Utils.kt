package com.example.medicineadvisor.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object Utils {

    val noInternetMsg ="No Internet Connection!!"

    fun hideKeyboard(ctx : Context,v :View){
        var imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken,0)
    }

    @JvmStatic
    @BindingAdapter("app:errorText")
    fun errorField(textInputLayout: TextInputLayout,error : String?){
        if (error.equals("")){
            textInputLayout.error = null
        } else {
            textInputLayout.error = error
            textInputLayout.errorIconDrawable = null
        }
    }



}