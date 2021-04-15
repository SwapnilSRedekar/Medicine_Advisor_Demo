package com.example.medicineadvisor.retrofit

import com.example.medicineadvisor.model.MedicineFields
import com.example.medicineadvisor.model.MedicineList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MedicineInterface {

    @GET("medicines")
    fun getMedicines(@Query("usage") use : String?):Call<MutableList<MedicineList>>

    @POST("medicines/")
    fun addMedicine(@Body newMedicine : MedicineFields ) : Call<MedicineFields>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "medicines/", hasBody = true)
    fun deleteMedicine(@Field("med_name") med_name: String): Call<Unit>

}
