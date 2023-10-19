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
import com.example.recipeapp.application.RecipeApplication
import com.example.recipeapp.model.*
import com.example.recipeapp.model.Result
import com.example.recipeapp.repository.CategoryRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class CategoryViewModel(private val app:Application, private val categoryRepository: CategoryRepository): AndroidViewModel(app)
{
    val categoryList=MutableLiveData<com.example.recipeapp.model.Result<CategorResponse>>()
    val randomRecipe=MutableLiveData<com.example.recipeapp.model.Result<MealResponse>>()
    val searchRecipeList=MutableLiveData<com.example.recipeapp.model.Result<MealResponse>>()
    val recipeDeatils=MutableLiveData<com.example.recipeapp.model.Result<MealResponse>>()
    val ingredientsList=MutableLiveData<com.example.recipeapp.model.Result<IngredientResponse>>()
    val areaList=MutableLiveData<com.example.recipeapp.model.Result<AreaResponse>>()




    fun getCategories()=viewModelScope.launch {
        getAllCategory()}

    fun getRandomRecipes()=viewModelScope.launch {
        getRecipeData()
    }

    fun getRecipeBySearch(recipeName:String)=viewModelScope.launch {
        getRecipeByFilter(recipeName)
    }

    fun getRecipeDetails(recipeId:String)=viewModelScope.launch {
        getRecipeById(recipeId)
    }

    fun getAllIngredientsList()=viewModelScope.launch {
        getIngredients()
    }

    fun getAllAreaList()=viewModelScope.launch {
       getAreas()
    }


     private suspend fun getAllCategory()
     {
         categoryList.postValue(com.example.recipeapp.model.Result.Loading())
         try {
                     if(hasInternetConnection())
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

    private suspend fun getRecipeData()
    {
        randomRecipe.postValue(com.example.recipeapp.model.Result.Loading())
        try {
               if (hasInternetConnection())
               {
                   val response=categoryRepository.getRandomRecipeData()
                   randomRecipe.postValue(handleRandomResponse(response=response))
               }
            else
               {
                   randomRecipe.postValue(com.example.recipeapp.model.Result.Error(message = "Please check your internet"))
               }
        }
        catch (t:Throwable)
        {
            when(t)
            {
                is IOException->randomRecipe.postValue(com.example.recipeapp.model.Result.Error(message = "No Internet"))
                else->    randomRecipe.postValue(com.example.recipeapp.model.Result.Error(message = "An Unexpected Error Occurred"))
            }
        }
    }

    private suspend fun getRecipeByFilter(searchQuery:String)
    {
        searchRecipeList.postValue(com.example.recipeapp.model.Result.Loading())
        try {
            if (hasInternetConnection())
            {
                val response=categoryRepository.getRecipeBySearch(searchQuery)
                searchRecipeList.postValue(handleSearchResponse(response=response))
            }
            else
            {
                searchRecipeList.postValue(com.example.recipeapp.model.Result.Error(message = "No Internet"))
            }
        }
        catch (t:Throwable)
        {
            when(t)
            {
                is IOException->searchRecipeList.postValue(com.example.recipeapp.model.Result.Error(message = "Check your internet"))
                else->    searchRecipeList.postValue(com.example.recipeapp.model.Result.Error(message = "An Unexpected Error Occurred"))
            }
        }
    }

    private suspend fun getRecipeById(strRecipeId:String)
    {
        recipeDeatils.postValue(com.example.recipeapp.model.Result.Loading())
        try {
             if (hasInternetConnection())
             {
                 val response=categoryRepository.getRecipeDetailsById(strRecipeId)
                 recipeDeatils.postValue(handleRecipeIdResponse(response=response))
             }
            else
             {
                 recipeDeatils.postValue(com.example.recipeapp.model.Result.Error(message = "No Network"))
             }
        }
        catch (t:Throwable)
        {
            when(t)
            {
                is IOException->recipeDeatils.postValue(com.example.recipeapp.model.Result.Error(message = "Network Error"))
                else->recipeDeatils.postValue(com.example.recipeapp.model.Result.Error(message = "An Unexpected Error"))
            }
        }
    }

    private suspend fun getIngredients()
    {
        ingredientsList.postValue(com.example.recipeapp.model.Result.Loading())
        try {
             if (hasInternetConnection())
             {
                 val response=categoryRepository.getIngredientsList()
                 ingredientsList.postValue(handleIngredientListResponse(response=response))
             }
            else
             {
                 ingredientsList.postValue(com.example.recipeapp.model.Result.Error(message = "No Internet"))
             }
        }
        catch (t:Throwable)
        {
            when(t)
            {
                is IOException->ingredientsList.postValue(com.example.recipeapp.model.Result.Error(message = "No Network"))
                else->ingredientsList.postValue(com.example.recipeapp.model.Result.Error(message = "An Unexpected Error Occurred"))
            }
        }
    }

    private suspend fun getAreas()
    {
        areaList.postValue(com.example.recipeapp.model.Result.Loading())
        try {
            if (hasInternetConnection())
            {
                val response=categoryRepository.getAreaList()
                areaList.postValue(handleAreaListResponse(response=response))
            }
            else
            {
                areaList.postValue(com.example.recipeapp.model.Result.Error(message = "No Internet"))
            }
        }
        catch (t:Throwable)
        {
            when(t)
            {
                is IOException->areaList.postValue(com.example.recipeapp.model.Result.Error(message = "No Network"))
                else->areaList.postValue(com.example.recipeapp.model.Result.Error(message = "An Unexpected Error Occurred"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<RecipeApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
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

    private fun handleRandomResponse(response: Response<MealResponse>):com.example.recipeapp.model.Result<MealResponse>
    {
        if (response.isSuccessful)
        {
            response.body()?.let {
                return com.example.recipeapp.model.Result.Success(it)
            }
        }
        return com.example.recipeapp.model.Result.Error(message = "No Network")
    }

    private fun handleSearchResponse(response: Response<MealResponse>):com.example.recipeapp.model.Result<MealResponse>
    {
        if (response.isSuccessful)
        {
            response.body()?.let {
                return com.example.recipeapp.model.Result.Success(it)
            }
        }
        return com.example.recipeapp.model.Result.Error(message = "No Internet")
    }

    private fun handleRecipeIdResponse(response: Response<MealResponse>):com.example.recipeapp.model.Result<MealResponse>
    {
        if (response.isSuccessful)
        {
            response.body()?.let {
                return com.example.recipeapp.model.Result.Success(it)
            }
        }
        return com.example.recipeapp.model.Result.Error(message="No Network")
    }

    private fun handleIngredientListResponse(response: Response<IngredientResponse>):com.example.recipeapp.model.Result<IngredientResponse>
    {
       if (response.isSuccessful)
       {
           response.body()?.let {
               return com.example.recipeapp.model.Result.Success(it)
           }
       }
        return com.example.recipeapp.model.Result.Error(message = "No Network")
    }

    private fun handleAreaListResponse(response: Response<AreaResponse>):com.example.recipeapp.model.Result<AreaResponse>
    {
        if (response.isSuccessful)
        {
            response.body()?.let {
                return com.example.recipeapp.model.Result.Success(it)
            }
        }
        return com.example.recipeapp.model.Result.Error(message = "No Network")
    }

    fun getIngredients(meal:MealData):String
    {
        var ingredients=""

        if (meal.strIngredient1?.isNotEmpty()==true)
        {
           ingredients= ingredients + " "+meal.strMeasure1+ " "+meal.strIngredient1 +" \n"
        }

        if (meal.strIngredient2?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure2+ " "+meal.strIngredient2 +" \n"
        }

        if (meal.strIngredient3?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure3+ " "+meal.strIngredient3+" \n"
        }

        if (meal.strIngredient4?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure4+ " "+meal.strIngredient4+" \n"
        }

        if (meal.strIngredient5?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure5+ " "+meal.strIngredient5+" \n"
        }

        if (meal.strIngredient6?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure6+ " "+meal.strIngredient6+" \n"
        }

        if (meal.strIngredient7?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure7+ " "+meal.strIngredient7+" \n"
        }

        if (meal.strIngredient8?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure8+ " "+meal.strIngredient8+" \n"
        }

        if (meal.strIngredient9?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure9+ " "+meal.strIngredient9+" \n"
        }

        if (meal.strIngredient10?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure10+ " "+meal.strIngredient10+" \n"
        }

        if (meal.strIngredient11?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure11+ " "+meal.strIngredient11+" \n"
        }

        if (meal.strIngredient12?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure12+ " "+meal.strIngredient12+" \n"
        }

        if (meal.strIngredient13?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure13+ " "+meal.strIngredient13+" \n"
        }

        if (meal.strIngredient14?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure14+ " "+meal.strIngredient14+" \n"
        }

        if (meal.strIngredient15?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure15+ " "+meal.strIngredient15+" \n"
        }

        if (meal.strIngredient16?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure16+ " "+meal.strIngredient16+" \n"
        }

        if (meal.strIngredient17?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure17+ " "+meal.strIngredient17+" \n"
        }

        if (meal.strIngredient18?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure18+ " "+meal.strIngredient18+" \n"
        }

        if (meal.strIngredient19?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure19+ " "+meal.strIngredient19+" \n"
        }

        if (meal.strIngredient20?.isNotEmpty()==true)
        {
            ingredients=ingredients+ " "+meal.strMeasure20+ " "+meal.strIngredient20+" \n"
        }
        return ingredients
    }
}