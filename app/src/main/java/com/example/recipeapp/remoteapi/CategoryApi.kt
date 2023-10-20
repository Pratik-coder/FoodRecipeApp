package com.example.recipeapp.remoteapi

import android.telecom.Call
import com.example.recipeapp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi
{
   @GET("/api/json/v1/1/categories.php")
   suspend fun getAllCategoriesList():Response<CategorResponse>

   @GET("/api/json/v1/1/random.php")
   suspend fun getRandomRecipes():Response<MealResponse>

   @GET("/api/json/v1/1/search.php")
   suspend fun searchRecipe(@Query ("s") strQuery:String):Response<MealResponse>

   @GET("/api/json/v1/1/lookup.php")
   suspend fun getRecipeById(@Query("i") recipeId:String):Response<MealResponse>

   @GET("/api/json/v1/1/list.php?i=list")
   suspend fun getAllIngredients():Response<FilterResponse>

   @GET("/api/json/v1/1/list.php?a=list")
   suspend fun getAllAreas():Response<FilterResponse>

   @GET("api/json/v1/1/filter.php")
   suspend fun getFilterByIngredient(@Query("i") ingredient:String):Response<FilterResponse>

   @GET("api/json/v1/1/filter.php")
   suspend fun getFilterByArea(@Query("a") area:String):Response<FilterResponse>

   @GET("api/json/v1/1/filter.php")
   suspend fun getFilterByCategory(@Query("c") category:String):Response<FilterResponse>
}