



package com.example.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.repository.CategoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


class CategoryDetailsViewModel @AssistedInject constructor(@Assisted private val categoryRepository: CategoryRepository, strIdCategory:String):
    ViewModel()
{
   val categoryData=categoryRepository.getByCategoryId(strIdCategory)

   /* @AssistedFactory
   interface CategoryDetailsViewModelFactory
    {
        fun create(idCategory:String):CategoryDetailsViewModel
    }



@Suppress("UNCHECKED_CAST")

    companion object {
        fun provideFactory(assistedFactory: CategoryDetailsViewModelFactory, idCategory: String):
                ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(CategoryDetailsViewModel::class.java))
                {
                    return assistedFactory.create(idCategory) as T
                }
              throw IllegalArgumentException("Unknown ViewModel Class")

            }
        }
    }*/
    }



