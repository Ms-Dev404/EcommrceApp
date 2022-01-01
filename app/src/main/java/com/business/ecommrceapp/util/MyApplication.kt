package com.business.ecommrceapp.util

import android.app.Application
import com.business.ecommrceapp.RetrofitClient
import com.business.ecommrceapp.network.ApiService

class MyApplication:Application() {

   companion object{

     var apiService:ApiService?=null
   }

    override fun onCreate() {
        super.onCreate()

        apiService = apiService ?: RetrofitClient().initRetrofit().create(ApiService::class.java)
    }


}