
package com.example.recipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.repository.CategoryRepository
import javax.inject.Inject


class CategoryViewModelFactory @Inject constructor(private val app:Application,private val categoryRepository: CategoryRepository):ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(app,categoryRepository ) as T
        }
         throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

