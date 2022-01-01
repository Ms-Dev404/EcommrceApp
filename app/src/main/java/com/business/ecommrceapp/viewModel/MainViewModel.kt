package com.business.ecommrceapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.business.ecommrceapp.*
import com.business.ecommrceapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel(private val apiService: ApiService):ViewModel() {

    private val categoryResponse = MutableLiveData<DataFetchState<List<CategoryItem>>>()
    private val subCategoryResponse = MutableLiveData<DataFetchState<List<SubCategoryItem>>>()

    fun getAllCategories(): MutableLiveData<DataFetchState<List<CategoryItem>>> {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                categoryResponse.postValue(DataFetchState.Loading(Info.LOADING))
                val response = apiService.getCategories()

                response.apply {

                    if (isSuccessful) {

                        if (!body().isNullOrEmpty()) {

                            categoryResponse.postValue(
                                DataFetchState.Success(
                                    Info.SUCCESS,
                                    body()!!
                                )
                            )
                        } else {

                            categoryResponse.postValue(
                                DataFetchState.Failure(
                                    Info.ERROR,
                                    java.lang.Exception("Data is empty!")
                                )
                            )
                        }

                    } else {

                        categoryResponse.postValue(
                            DataFetchState.Failure(
                                Info.ERROR,
                                java.lang.Exception("Something went wrong!")
                            )
                        )

                    }

                }


            } catch (e: Exception) {

                categoryResponse.postValue(DataFetchState.Failure(Info.ERROR, e))
            }

        }
      return categoryResponse
    }





        fun getSubCategories(parentId: Int):MutableLiveData<DataFetchState<List<SubCategoryItem>>> {

            viewModelScope.launch(Dispatchers.IO) {

                try {
                    subCategoryResponse.postValue(DataFetchState.Loading(Info.LOADING))

                    val response = apiService.getSubCategories(parentId)

                    response.apply {

                      if(isSuccessful){

                          if(!body().isNullOrEmpty()) {

                              subCategoryResponse.postValue(DataFetchState.Success(Info.SUCCESS, body()!!))

                          }else{

                           subCategoryResponse.postValue(DataFetchState.Failure(Info.ERROR,java.lang.Exception("Data is empty")))

                          }

                          }else{

                          subCategoryResponse.postValue(DataFetchState.Failure(Info.ERROR,java.lang.Exception("Something went wrong!")))
                          }
                      }


                }catch (e:java.lang.Exception){

                    subCategoryResponse.postValue(DataFetchState.Failure(Info.ERROR,e))
                }
            }
          return subCategoryResponse
        }

   fun getProducts(catId:Int)=Pager(PagingConfig(pageSize = 1)) {
       ProductsPageSource(apiService,catId)
   }.flow.cachedIn(viewModelScope)


}



