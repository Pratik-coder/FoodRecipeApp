package com.example.recipeapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannelGroup
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.CategorResponse
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.utils.NetworkConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

@SuppressLint("StaticFieldLeak")
class CategoryViewModel(private val app:Application, private val categoryRepository: CategoryRepository): AndroidViewModel(app)
{
    val categoryList=MutableLiveData<com.example.recipeapp.model.Result<CategorResponse>>()
    private val context:Context=app.applicationContext


    @RequiresApi(Build.VERSION_CODES.M)
    fun getCategories()=viewModelScope.launch {
        getAllCategory() }


     @RequiresApi(Build.VERSION_CODES.M)
     private suspend fun getAllCategory()
     {
         categoryList.postValue(com.example.recipeapp.model.Result.Loading())
         try {
                     if(checkNetworkConnection())
                     {
                        val response=categoryRepository.getCategories()
                         categoryList.postValue(handleCategoryResponse(response=response))
                     }
             else
                     {
                         categoryList.postValue(com.example.recipeapp.model.Result.Error(message = "No Internet Connection"))
                     }
         }
         catch (t:Throwable)
         {
             when(t)
             {
                 is IOException ->categoryList.postValue(com.example.recipeapp.model.Result.Error(message = "Network Failure"))
                 else->categoryList.postValue(com.example.recipeapp.model.Result.Error(message = "Error"))
             }
         }
     }

     @RequiresApi(Build.VERSION_CODES.M)
     private fun checkNetworkConnection():Boolean
     {
         val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val network=connectivityManager.activeNetwork
         val networkCapabilities=connectivityManager.getNetworkCapabilities(network)

             return networkCapabilities!=null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))||
                     (networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))

     }

    private fun handleCategoryResponse(response:Response<CategorResponse>):com.example.recipeapp.model.Result<CategorResponse>
    {
        if (response.isSuccessful)
        {
            response.body()?.let {
                return com.example.recipeapp.model.Result.Success(it)
            }
        }
        return com.example.recipeapp.model.Result.Error(message = response.message())
    }
}