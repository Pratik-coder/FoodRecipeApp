package com.example.recipeapp.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityRecipeDetailsBinding
import com.example.recipeapp.model.MealData
import com.example.recipeapp.model.MealResponse
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.viewmodel.CategoryViewModel
import com.example.recipeapp.viewmodel.CategoryViewModelFactory

class RecipeDetails : AppCompatActivity() {

    private lateinit var binding:ActivityRecipeDetailsBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_recipe_details)
        binding=ActivityRecipeDetailsBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        progressDialog= ProgressDialog(this)
        val repository=CategoryRepository()
        val viewModelFactory=CategoryViewModelFactory(application,repository)
        viewModel=ViewModelProvider(this,viewModelFactory)[CategoryViewModel::class.java]
        val receiveBundle=intent.extras

        if (receiveBundle!=null && receiveBundle.containsKey("RecipeID"))
        {
            val recipeId=receiveBundle.getString("RecipeID")
            if (recipeId != null) {
                viewModel.getRecipeDetails(recipeId)
            }
        }

        viewModel.recipeDeatils.observe(this, Observer {
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
                        val meal=it.meals?.get(0)
                        setRecipeDetails(meal)
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

    private fun setRecipeDetails(mealData: MealData?)
    {
        Glide.with(binding.root).load(mealData?.strMealThumb).into(binding.mealImage)
        binding.mealText.text=mealData?.strMeal
        binding.recipeIngredient.text=mealData?.let {viewModel.getIngredients(it)}
        binding.recipeInstruction.text=mealData?.strInstructions
    }
}