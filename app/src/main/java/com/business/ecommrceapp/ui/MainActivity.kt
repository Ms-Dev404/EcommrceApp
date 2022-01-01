package com.business.ecommrceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.business.ecommrceapp.util.MyApplication.Companion.apiService
import com.business.ecommrceapp.databinding.ActivityMainBinding
import com.business.ecommrceapp.adapter.CategoryRecyclerViewAdapter
import com.business.ecommrceapp.adapter.LoadingStateAdapter
import com.business.ecommrceapp.adapter.ProductsAdapter
import com.business.ecommrceapp.util.DataFetchState
import com.business.ecommrceapp.util.Info
import com.business.ecommrceapp.viewModel.MainViewModel
import com.business.ecommrceapp.viewModel.ViewModelFactory
import com.business.ecommrceapp.models.CategoryItem
import com.business.ecommrceapp.models.SubCategoryItem
import com.business.ecommrceapp.util.RecyclerViewItemClickListener
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity(), RecyclerViewItemClickListener {
    private var viewBinding:ActivityMainBinding?=null
    private lateinit var viewModel:MainViewModel
    private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter<CategoryItem>
    private lateinit var subCategoryRecyclerViewAdapter: CategoryRecyclerViewAdapter<SubCategoryItem>
    private val categoryList=ArrayList<CategoryItem>()
    private val subCategoryList=ArrayList<SubCategoryItem>()
    private val productAdapter= ProductsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding!!.root)
        val factoryVm = apiService?.let { ViewModelFactory(it) }
        viewModel = ViewModelProvider(this, factoryVm!!)[MainViewModel::class.java]
        categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(categoryList, this)
        subCategoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(subCategoryList, this)

        viewBinding!!.apply {
            rvCategory.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            rvCategory.adapter = categoryRecyclerViewAdapter

            rvSub.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            rvSub.adapter = subCategoryRecyclerViewAdapter

            rvProducts.layoutManager=LinearLayoutManager(root.context)
            rvProducts.adapter=productAdapter.withLoadStateFooter(LoadingStateAdapter())

        }


        viewModel.getAllCategories().observe(this, { categoryResponse ->

            categoryResponse.let { fetched ->

                when (fetched) {

                    is DataFetchState.Loading -> {
                        if (fetched.info == Info.LOADING) {

                            Toast.makeText(this, "Fetch started...", Toast.LENGTH_SHORT).show()
                        }

                    }

                    is DataFetchState.Success -> {

                        if (fetched.info == Info.SUCCESS) {

                            Toast.makeText(this, "Fetch Completed...", Toast.LENGTH_SHORT).show()

                            loadProducts(0)

                        }


                        if (!fetched.responseData.isNullOrEmpty()) {

                            categoryList.addAll(fetched.responseData)
                            categoryRecyclerViewAdapter.notifyItemChanged(categoryList.size)
                        }

                    }

                    is DataFetchState.Failure -> {

                        if (fetched.info == Info.ERROR) {

                            Toast.makeText(this, fetched.e.localizedMessage, Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                }

            }


        })




    }

   private fun loadProducts(categoryId:Int){

        lifecycleScope.launch(IO) {

            viewModel.getProducts(categoryId).collect {productPagedData->

                    productAdapter.submitData(productPagedData)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewBinding=null

    }

    override fun onItemClicked(dataItem: Any?) {

        if(dataItem is CategoryItem){

                getSubCategories(dataItem.id)

        }else if(dataItem is SubCategoryItem){

            loadProducts(dataItem.id)
        }
    }

    private fun getSubCategories(id:Int){

        viewModel.getSubCategories(id).observe(this,{fetched->


                when(fetched){

                    is DataFetchState.Loading->{

                    }

                    is DataFetchState.Success->{


                        subCategoryList.clear()
                        subCategoryList.add(SubCategoryItem("All",0))
                        subCategoryList.addAll(fetched.responseData)
                        subCategoryRecyclerViewAdapter.notifyDataSetChanged()
                        subCategoryRecyclerViewAdapter.selected=-1

                    }

                    is DataFetchState.Failure->{

                        if(fetched.info== Info.ERROR){

                            Toast.makeText(this,fetched.e.localizedMessage,Toast.LENGTH_SHORT).show()
                        }

                    }
                }



        })


    }
}