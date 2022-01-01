package com.business.ecommrceapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Products(@SerializedName("name") @Expose val name:String, @SerializedName("rating") @Expose val rating:String, @SerializedName("price") @Expose val productPrice: ProductPrice, @SerializedName("images") @Expose val images:List<ProductImage>)

data class ProductPrice(@SerializedName("sale_price")@Expose val price:String)
data class ProductImage(@SerializedName("image_url")@Expose val imageUrl:String)


