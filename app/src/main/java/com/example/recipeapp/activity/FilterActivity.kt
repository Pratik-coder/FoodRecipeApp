package com.example.recipeapp.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.QueryAdapter
import com.example.recipeapp.databinding.ActivityFilterBinding
import com.example.recipeapp.databinding.ActivityRecipeDetailsBinding
import com.example.recipeapp.model.QueryType
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.viewmodel.CategoryViewModel
import com.example.recipeapp.viewmodel.CategoryViewModelFactory

class FilterActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityFilterBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var progressDialog: ProgressDialog
    private lateinit var queryAdapter: QueryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_filter)
        binding=ActivityFilterBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        progressDialog= ProgressDialog(this)
        val repository= CategoryRepository()
        val viewModelFactory= CategoryViewModelFactory(application,repository)
        viewModel= ViewModelProvider(this,viewModelFactory)[CategoryViewModel::class.java]

        setUpRecyclerView()

        val bundle=intent.extras
        if (bundle!=null && bundle.containsKey("Filter"))
        {
            val filterQueryType=bundle.getSerializable("Filter") as QueryType
            val strQueryType=filterQueryType.strQueryType
            val strQuery=filterQueryType.strQuery
            when(strQueryType)
            {
                "i"->{
                        viewModel.getIngredientListByName(strQuery)
                }

                "a"->
                {
                        viewModel.getAreaListByName(strQuery)
                }

                "c"->
                {
                    viewModel.getCategoryListByName(strQuery)
                }
            }
        }

       /* queryAdapter.setOnItemClickListener {
            val intent=Intent(this@FilterActivity,RecipeDetails::class.java)
            intent.putExtra("RecipeID",it.idMeal)
        }
        startActivity(intent)*/
        queryAdapter.setOnItemClickListener {
            val bundleRecipe=Bundle().apply {
                putString("RecipeID",it.idMeal)
            }
            val intent=Intent(this,RecipeDetails::class.java)
            intent.putExtras(bundleRecipe)
            startActivity(intent)
        }

        viewModel.filtersByList.observe(this, Observer {
            response->
            when(response)
            {
                is com.example.recipeapp.model.Result.Loading->
                {
                    progressDialog.setMessage("Loading...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                }
                is com.example.recipeapp.model.Result.Success->
                {
                    progressDialog.dismiss()
                    response.data?.let {
                        queryAdapter.differList.submitList(it.meals.toList())
                    }
                }
                is com.example.recipeapp.model.Result.Error->
                {
                    progressDialog.dismiss()
                    response.message?.let {
                        Toast.makeText(this,"An Error Occurred",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun setUpRecyclerView()
    {
        queryAdapter= QueryAdapter()
        binding.rvFilter.apply {
            adapter=queryAdapter
            layoutManager=LinearLayoutManager(this@FilterActivity)
        }
    }
}