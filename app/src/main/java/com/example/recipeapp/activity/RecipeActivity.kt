package com.example.recipeapp.activity

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
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

class RecipeActivity : AppCompatActivity(),View.OnClickListener{

    private lateinit var categoryViewModel: CategoryViewModel
    private val categoryRepository: CategoryRepository = CategoryRepository()
    private lateinit var mAdapter: CategoryAdapter
    private lateinit var binding:ActivityRecipeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecipeBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        initView()
        observer()
    }

    override fun onStart() {
        super.onStart()
        checkInternet()
    }


    private fun initView() {
        categoryViewModel = ViewModelProvider(this, CategoryViewModelFactory(categoryRepository))
                .get(CategoryViewModel::class.java)
        binding.tvconnection.setOnClickListener(this)
    }

    private fun checkInternet() {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.str_checkinternet), Toast.LENGTH_SHORT).show()
            binding.tvconnection.visibility= VISIBLE
        }
    }


    private fun isNetworkAvailable(): Boolean {
        val networkConnection= NetworkConnection()
        return networkConnection.isNetworkAvailable(this)
    }

    private fun observer()
    {
        categoryViewModel.getCategoryByList().observe(this, Observer<List<CategoryData>>
        {
            if (it!=null)
            {
                mAdapter= CategoryAdapter(this,it,object :CategoryAdapter.OnItemClickListener
                {
                    override fun onCategoryClick(categoryData: CategoryData) {
                        val intent=Intent(this@RecipeActivity,CategoryDetails::class.java)
                        intent.putExtra("strCategory",categoryData.strCategory)
                        intent.putExtra("strCategoryThumb",categoryData.strCategoryThumb)
                        intent.putExtra("strCategoryDescription",categoryData.strCategoryDescription)
                        startActivity(intent)
                    }

                })
                val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                binding.rvcategory.layoutManager=layoutManager
                binding.rvcategory.adapter=mAdapter
            }
        })
    }
    private fun getAllList() = categoryViewModel.getCategoryByList()

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

   override fun onClick(view: View?) {
          if (isNetworkAvailable())
          {
              observer()
          }
       binding.tvconnection.visibility= GONE

    }
}