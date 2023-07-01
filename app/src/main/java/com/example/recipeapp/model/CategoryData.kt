package com.example.recipeapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeapp.constants.Constant
import com.google.gson.annotations.SerializedName


@Entity(tableName = Constant.DATA_BASENAME)
data class CategoryData(
 @PrimaryKey
@SerializedName("idCategory") var idCategory:String,@SerializedName("strCategory") var strCategory:String,
@SerializedName("strCategoryThumb") var strCategoryThumb:String,@SerializedName("strCategoryDescription")var strCategoryDescription:String
)
