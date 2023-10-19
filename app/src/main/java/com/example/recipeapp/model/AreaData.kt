package com.example.recipeapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AreaData(@SerializedName("strArea")
                    @Expose val strArea:String)
