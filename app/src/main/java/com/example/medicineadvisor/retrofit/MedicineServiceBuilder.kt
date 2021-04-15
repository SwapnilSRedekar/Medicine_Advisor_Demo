package com.example.medicineadvisor.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MedicineServiceBuilder {

    var BASE_URl = ""

    var loggs = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    var okHttp = OkHttpClient.Builder().addInterceptor(loggs)

    var api = Retrofit.Builder().baseUrl(BASE_URl).addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    var retrofit = api.build()

    fun <T> getBuild(se : Class<T>) : T{
        return retrofit.create(se)
    }

}