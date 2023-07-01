package com.example.recipeapp.remoteapi

import android.telecom.Call
import com.example.recipeapp.model.CategorResponse
import com.example.recipeapp.model.CategoryData
import retrofit2.http.GET

interface CategoryApi
{
   @GET("categories.php")
   fun getAllCategoriesList():retrofit2.Call<CategorResponse>
}