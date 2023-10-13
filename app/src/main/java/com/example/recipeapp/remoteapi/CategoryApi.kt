package com.example.recipeapp.remoteapi

import android.telecom.Call
import com.example.recipeapp.model.CategorResponse
import com.example.recipeapp.model.CategoryData
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi
{
   @GET("/api/json/v1/1/categories.php")
   suspend fun getAllCategoriesList():Response<CategorResponse>
}