package com.business.ecommrceapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubCategoryItem( @SerializedName("name")
                       @Expose
                       val catName:String,
                       @SerializedName("id")
                       @Expose
                       val id:Int)