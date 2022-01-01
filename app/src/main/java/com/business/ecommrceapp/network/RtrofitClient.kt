package com.business.ecommrceapp


import com.business.ecommrceapp.util.UrlRepo
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {


   private var retrofitClient:Retrofit?=null

    fun  initRetrofit():Retrofit{


        runBlocking {

            if (retrofitClient == null) {

                retrofitClient = Retrofit.Builder().baseUrl(UrlRepo.BSE_URL).client(setHttpClient()).addConverterFactory(GsonConverterFactory.create()).build()
            }

            }

      return retrofitClient!!
    }

    private fun setHttpClient():OkHttpClient=OkHttpClient.Builder().readTimeout(20,TimeUnit.SECONDS).connectTimeout(20,TimeUnit.SECONDS).build()
}