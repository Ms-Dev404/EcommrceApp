package com.business.ecommrceapp.network

import com.business.ecommrceapp.util.UrlRepo
import com.business.ecommrceapp.models.CategoryItem
import com.business.ecommrceapp.models.Products
import com.business.ecommrceapp.models.SubCategoryItem
import com.business.ecommrceapp.util.UrlRepo.PRODUCTS
import com.business.ecommrceapp.util.UrlRepo.SUB_CATEGORIES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(UrlRepo.CATEGORIES)
    suspend fun getCategories():Response<List<CategoryItem>>

    @GET(SUB_CATEGORIES)
    suspend fun getSubCategories(@Query("parent_id") parentId:Int):Response<List<SubCategoryItem>>

    @GET(PRODUCTS)
    suspend fun getAllProducts(@Query("parent_category_id")pcId:Int,@Query("category_id")catId:Int,@Query("store_id")storeId:Int,@Query("offset")offset:Int,@Query("limit")limit:Int,@Query("sort_by")sortBy:String,@Query("sort_type")sortType:String):Response<ArrayList<Products>>
   }