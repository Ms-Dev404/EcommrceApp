package com.business.ecommrceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.business.ecommrceapp.ProductsAdapter.*
import com.business.ecommrceapp.UrlRepo.IMAGE_URL
import com.business.ecommrceapp.databinding.ProductRowBinding

class ProductsAdapter:PagingDataAdapter<Products, VH>(DataComparator) {



    override fun onBindViewHolder(holder: VH, position: Int) {

        val product=getItem(position)

        if(product!=null){

            holder.bind(product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewBinding=ProductRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return VH(viewBinding)
    }

  object DataComparator:DiffUtil.ItemCallback<Products>() {
      override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {

        return oldItem==newItem
      }

      override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
          return oldItem==newItem
      }
  }

    class VH(private val binding:ProductRowBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(products: Products){

            binding.apply {

                if(!products.images.isNullOrEmpty()){

                ivProduct.load(IMAGE_URL+products.images[0].imageUrl)

                }
                tvPrdName.text=products.name
                tvPrdPrice.text="${products.productPrice.price} SAR"
                ratingBar.rating=products.rating.toFloat()
            }
        }
    }
}