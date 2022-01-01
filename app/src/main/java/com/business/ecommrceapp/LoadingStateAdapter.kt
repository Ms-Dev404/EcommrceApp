package com.business.ecommrceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.business.ecommrceapp.databinding.LoadStateRowLayoutBinding

class LoadingStateAdapter: LoadStateAdapter<LoadingStateAdapter.LoadStateViewHolder>() {


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val vieBinding=LoadStateRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return LoadStateViewHolder(vieBinding)
    }

    class LoadStateViewHolder(private val binding: LoadStateRowLayoutBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind( loadState: LoadState){

           binding.pbload.isVisible=loadState is LoadState.Loading
           binding.tvCaption.isVisible=loadState is LoadState.Loading

        }
    }
}



