package com.business.ecommrceapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.business.ecommrceapp.MyApplication.Companion.apiService
import com.business.ecommrceapp.network.ApiService

class ViewModelFactory(private val apiService: ApiService): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MainViewModel::class.java)){

            return  MainViewModel(apiService) as T

        }else{


            throw IllegalArgumentException("non viewModelClass")
        }


    }
}