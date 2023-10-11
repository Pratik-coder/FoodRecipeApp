package com.example.recipeapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeapp.constants.Constant
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class CategoryData(
@SerializedName("idCategory")
@Expose
var idCategory:String?=null,
@SerializedName("strCategory")
@Expose
var strCategory:String?=null,
@SerializedName("strCategoryThumb")
@Expose
var strCategoryThumb:String?=null,
@SerializedName("strCategoryDescription")
@Expose
var strCategoryDescription:String?=null
)
