package com.example.recipeapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategorResponse(@SerializedName("categories") @Expose var categories:List<CategoryData>?=null)
