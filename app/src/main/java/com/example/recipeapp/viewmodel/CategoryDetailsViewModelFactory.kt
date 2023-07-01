



package com.example.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.activity.CategoryDetails
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.repository.CategoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi


class CategoryDetailsViewModelFactory @AssistedInject constructor(@Assisted private val categoryRepository: CategoryRepository,private val stridCategory:String):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryDetailsViewModel::class.java)) {
            return CategoryDetailsViewModel(categoryRepository, strIdCategory = stridCategory) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}







