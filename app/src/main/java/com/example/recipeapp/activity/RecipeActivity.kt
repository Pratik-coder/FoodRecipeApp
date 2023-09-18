package com.example.recipeapp.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.`interface`.OnCategoryClickedListener
import com.example.recipeapp.adapter.CategoryAdapter
import com.example.recipeapp.databinding.ActivityRecipeBinding
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.utils.NetworkConnection
import com.example.recipeapp.viewmodel.CategoryViewModel
import com.example.recipeapp.viewmodel.CategoryViewModelFactory


class RecipeActivity : AppCompatActivity(){

    private lateinit var categoryViewModel: CategoryViewModel
    private val categoryRepository=CategoryRepository()
    private lateinit var mAdapter: CategoryAdapter
    private lateinit var binding:ActivityRecipeBinding
    private var progressDialog: ProgressDialog?=null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecipeBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        val viewModelFactory=CategoryViewModelFactory(application,categoryRepository)
        categoryViewModel=ViewModelProvider(this, viewModelFactory)[CategoryViewModel::class.java]
        categoryViewModel.getCategories()
        setUpRecyclerView()

        categoryViewModel.categoryList.observe(this,Observer
        {
            response->
            when(response)
            {
                is com.example.recipeapp.model.Result.Success->
                {
                    progressDialog=ProgressDialog(this)
                    progressDialog!!.dismiss()
                    response.data?.let {
                        mAdapter.differ.submitList(it.categories.toList())
                    }
                }
               is com.example.recipeapp.model.Result.Error->
               {
                   progressDialog=ProgressDialog(this)
                   progressDialog!!.dismiss()
                   response.message?.let {
                       Toast.makeText(this@RecipeActivity,"An Error occurred",Toast.LENGTH_SHORT).show()
                   }
               }

                is com.example.recipeapp.model.Result.Loading->
                {
                    if (progressDialog == null) {
                        progressDialog = ProgressDialog(this)
                        progressDialog!!.setMessage("Loading...")
                        progressDialog!!.setCancelable(false)
                    }
                    progressDialog?.show()
                }
            }
        })
    }



    private fun setUpRecyclerView()
    {
        mAdapter= CategoryAdapter()
        binding.rvcategory.apply {
            adapter=mAdapter
            layoutManager=LinearLayoutManager(this@RecipeActivity)
        }
    }


    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.str_exittitle))
            .setMessage(getString(R.string.str_exit_message))
            .setPositiveButton(getString(R.string.str_yes))
            { dialogInterface, _ ->
                dialogInterface.dismiss()
                super.onBackPressed()
            }
            .setNegativeButton(getString(R.string.str_no))
            { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }
}