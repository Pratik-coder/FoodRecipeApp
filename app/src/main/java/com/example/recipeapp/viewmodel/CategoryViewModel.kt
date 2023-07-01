package com.example.recipeapp.viewmodel

import android.app.NotificationChannelGroup
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.repository.CategoryRepository

class CategoryViewModel(private val categoryRepository: CategoryRepository): ViewModel()
{
    private var categoryLiveData:MutableLiveData<List<CategoryData>>? = null
    var categoryListData:LiveData<List<CategoryData>>?=null

    fun getCategoryByList():MutableLiveData<List<CategoryData>>
    {
       categoryLiveData=let{ categoryRepository.getCategoryList()}
        return categoryLiveData as MutableLiveData<List<CategoryData>>
    }

    fun getAllCategoryList(context: Context):LiveData<List<CategoryData>>?
    {
        categoryListData=CategoryRepository.getAllCategoriesByList(context)
        return categoryListData
    }


}