package com.example.recipeapp.model

import com.google.gson.annotations.SerializedName

data class CategorResponse(@SerializedName("categories") var categories:List<CategoryData>)
