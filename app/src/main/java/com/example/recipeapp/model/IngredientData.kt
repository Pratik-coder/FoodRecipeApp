package com.example.recipeapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IngredientData(
    @SerializedName("idIngredient")
    @Expose
    var idIngredient:String,

    @SerializedName("strIngredient")
    @Expose
    var strIngredient:String,

    @SerializedName("strDescription")
    @Expose
    var strDescription:String?=null,

    @SerializedName("strType")
    @Expose
    var strType:String?=null)
