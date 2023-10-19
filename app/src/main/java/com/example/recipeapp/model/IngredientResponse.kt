package com.example.recipeapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IngredientResponse(@SerializedName("meals")
                              @Expose val meals:List<IngredientData>)

