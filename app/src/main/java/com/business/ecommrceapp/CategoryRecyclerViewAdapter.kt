package com.business.ecommrceapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.business.ecommrceapp.databinding.CategoryRowBinding

class CategoryRecyclerViewAdapter<out T>(private val categoryList:List<T>, private val itemClickListener:RecyclerViewItemClickListener): RecyclerView.Adapter<CategoryRecyclerViewAdapter<T>.VH>() {
    var selected=-1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val viewBinding =
            CategoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return VH(viewBinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val category = categoryList[position]

        if (category != null) {

            holder.bind(category,position)
        }
    }

    override fun getItemCount() = categoryList.size


  inner class VH(private val binding: CategoryRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category:Any,pos:Int) {

            binding.apply {

                if(selected==pos){

                    cardCat.setBackgroundColor(Color.parseColor("#7f9259"))
                    tvCategory.setTextColor(Color.WHITE)
                }else{

                    cardCat.setBackgroundColor(Color.parseColor("#16000000"))
                    tvCategory.setTextColor(Color.BLACK)
                }

                if(category is CategoryItem ) {

                    tvCategory.text = category.catName.lowercase()

                } else if(category is SubCategoryItem){

                    tvCategory.text = category.catName.lowercase()

                }else{

                    throw IllegalArgumentException("unexpected object!")
                }

                cardCat.setOnClickListener {

                    itemClickListener.onItemClicked(category)
                    selected=bindingAdapterPosition
                    cardCat.setBackgroundColor(Color.parseColor("#7f9259"))
                    tvCategory.setTextColor(Color.WHITE)
                    notifyDataSetChanged()


                }
            }

        }

    }
}
