
package com.example.recipeapp.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkConnection: ConnectivityManager.NetworkCallback()
{
    fun isNetworkAvailable(context: Context):Boolean
    {
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // var activeNetworkInfo:NetworkInfo?=null
        val  activeNetworkInfo=connectivityManager.activeNetworkInfo
        return activeNetworkInfo!=null && activeNetworkInfo.isConnectedOrConnecting
    }
}
