package com.example.recipeapp.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.constants.Constant
import com.example.recipeapp.model.CategorResponse
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.model.MealResponse
import com.example.recipeapp.remoteapi.CategoryApi
import com.example.recipeapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CategoryRepository
{
       suspend fun getCategories():Response<CategorResponse>
       {
           return RetrofitClient.getInstance().create(CategoryApi::class.java).getAllCategoriesList()
       }

    suspend fun getRandomRecipeData():Response<MealResponse>
    {
        return RetrofitClient.getInstance().create(CategoryApi::class.java).getRandomRecipes()
    }

    suspend fun getRecipeBySearch(strQuery:String):Response<MealResponse>
    {
        return RetrofitClient.getInstance().create(CategoryApi::class.java).searchRecipe(strQuery)
    }
}

