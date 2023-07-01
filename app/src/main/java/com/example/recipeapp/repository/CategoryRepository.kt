package com.example.recipeapp.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.constants.Constant
import com.example.recipeapp.model.CategorResponse
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.remoteapi.CategoryApi
import com.example.recipeapp.retrofit.RetrofitClient
import com.example.recipeapp.roomdatabase.CategoryRoomDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepository()
{

    companion object {
        private lateinit var caterqoryDatabase: CategoryRoomDatabase

        private fun initializeDatabase(context: Context): CategoryRoomDatabase {
            return CategoryRoomDatabase.getDatabase(context)
        }

        fun getAllCategoriesByList(context: Context): LiveData<List<CategoryData>> {
            caterqoryDatabase = initializeDatabase(context)
            return caterqoryDatabase.categoryDao().getAllCategories()
        }
    }

    fun getByCategoryId(idCategory:String):CategoryData
    {
       return caterqoryDatabase.categoryDao().getCategoryById(idCategory)
    }


    fun getCategoryList():MutableLiveData<List<CategoryData>>
    {
         val categoryList=MutableLiveData<List<CategoryData>>()
         val call=RetrofitClient.getInstance().create(CategoryApi::class.java).getAllCategoriesList()
         call.enqueue(object :Callback<CategorResponse>
         {
             override fun onResponse(
                 call: Call<CategorResponse>,
                 response: Response<CategorResponse>
             ) {
                 if (response.isSuccessful) {
                     val body = response.body()
                     if (body != null) {
                         val foodCateoryList = mutableListOf<CategoryData>()
                         body.categories.forEach {
                             foodCateoryList.add(
                                 CategoryData(
                                     it.idCategory,
                                     it.strCategory,
                                     it.strCategoryThumb,
                                     it.strCategoryDescription
                                 )
                             )
                         }
                         categoryList.value = foodCateoryList

                     }
                 }
             }
             override fun onFailure(call: Call<CategorResponse>, t: Throwable) {
                Constant.apiRequest=true
                Constant.apiError=t.localizedMessage as String
             }
         })
        return categoryList
    }
}

