package com.business.ecommrceapp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.business.ecommrceapp.network.ApiService
import com.business.ecommrceapp.models.Products

class ProductsPageSource(private val apiService:ApiService,val categoryId:Int): PagingSource<Int, Products>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Products> {

        try {
            val pageOffset = params.key ?:1
            val response = apiService.getAllProducts(136,categoryId,2,pageOffset,20,"sale_price","DESC")
            val responseData = mutableListOf<Products>()

            response.apply{

                Log.d("TAG", "load:${raw()} ")

                val data = response.body()?: emptyList()

              if(isSuccessful){

                  if(!data.isNullOrEmpty()) {

                      responseData.addAll(data)

                  }else{

                      return LoadResult.Error(Throwable("Data is empty!"))
                  }
              }else{

                  return LoadResult.Error(Throwable("something went wrong!"))
              }

            }


            val prevKey = if (pageOffset == 1) null else pageOffset - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = pageOffset.plus(1)
            )
        } catch (e: Exception) {
            Log.e("TAG", "load:${e.localizedMessage} ")
            return LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, Products>)=0
}