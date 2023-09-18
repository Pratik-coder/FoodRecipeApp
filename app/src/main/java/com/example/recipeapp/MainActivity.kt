/*
package com.example.recipeapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapter.CategoryAdapter
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.viewmodel.CategoryViewModel
import com.example.recipeapp.viewmodel.CategoryViewModelFactory


class MainActivity : AppCompatActivity()
{

   // private val categoryRepository:CategoryRepository= CategoryRepository()
    private lateinit var categoryRepository:CategoryRepository
    private lateinit var categoryViewModel:CategoryViewModel
    private lateinit var factory: CategoryViewModelFactory

    private lateinit var textViewNetworkStatus:TextView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recyclerViewCategoryList: RecyclerView
    private  var isConnected:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        observer()
    }




    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart()
    {
        super.onStart()
     //   handleNetworkChanges()
    }

    private fun initView()
    {
        textViewNetworkStatus=findViewById(R.id.tv_connection)
        recyclerViewCategoryList=findViewById(R.id.rv_category)
       */
/* factory= CategoryViewModelFactory(categoryRepository)
        categoryViewModel=ViewModelProvider(this,factory)[CategoryViewModel::class.java]*//*

        categoryViewModel=ViewModelProviders.of(this,CategoryViewModelFactory(categoryRepository)).get(CategoryViewModel::class.java)
    }



    */
/*private fun checkNetwork():Boolean
    {
        netWorkConnection.getNetwork(this)
        return isConnected
    }

    private fun handleNetworkChanges()
    {
        if (checkNetwork())
        {
            if (categoryAdapter.itemCount==0)getAllList()
            textViewNetworkStatus.text=getString(R.string.str_cbackonline)
        }

        else
        {
            textViewNetworkStatus.text=getString(R.string.str_checknet)
        }
    }


*//*

    private fun getAllList()= categoryViewModel.getCategoryByList()


    override fun onBackPressed()
    {
           AlertDialog.Builder(this)
               .setTitle(getString(R.string.str_exittitle))
               .setMessage(getString(R.string.str_exit_message))
               .setPositiveButton(getString(R.string.str_yes))
               {
                   dialogInterface,_->
                   dialogInterface.dismiss()
                   super.onBackPressed()
               }
               .setNegativeButton(getString(R.string.str_no))
               {
                   dialogInterface,_->
                   dialogInterface.dismiss()
               }
               .show()
    }
}*/
