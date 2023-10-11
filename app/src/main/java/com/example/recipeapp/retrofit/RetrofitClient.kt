package com.example.recipeapp.retrofit

import androidx.viewbinding.BuildConfig
import com.example.recipeapp.constants.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient
{
    fun getInstance():Retrofit
    {
       /* var mHttpLoggingInterceptor=HttpLoggingInterceptor()
           .setLevel(HttpLoggingInterceptor.Level.BODY)

        mHttpLoggingInterceptor.level=if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        var mOkHttpClient=OkHttpClient
            .Builder().addInterceptor(mHttpLoggingInterceptor)
            .build()*/

        var retrofit:Retrofit=Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
          //  .client(mOkHttpClient)
            .build()
        return retrofit
    }
}