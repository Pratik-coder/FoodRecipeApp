package com.example.recipeapp.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.recipeapp.constants.Constant
import com.example.recipeapp.constants.Constant.DATA_BASENAME
import com.example.recipeapp.model.CategorResponse
import com.example.recipeapp.model.CategoryData

@Dao
interface CategoryDao
{
   @Query("SELECT * FROM ${DATA_BASENAME}")
   fun getAllCategories():LiveData<List<CategoryData>>

   @Query("SELECT * FROM ${DATA_BASENAME} WHERE  idCategory =:idCategory")
   fun getCategoryById(idCategory:String):CategoryData
}