
package com.example.recipeapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityCategoryDetailsBinding
import com.example.recipeapp.databinding.ActivityRecipeBinding
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.utils.NetworkConnection
import com.example.recipeapp.viewmodel.CategoryDetailsViewModel
import com.example.recipeapp.viewmodel.CategoryDetailsViewModelFactory
import com.example.recipeapp.viewmodel.CategoryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.net.IDN
import javax.inject.Inject
import javax.inject.Provider

class CategoryDetails : AppCompatActivity() {

    private lateinit var categoryDetailsBinding: ActivityCategoryDetailsBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            categoryDetailsBinding = ActivityCategoryDetailsBinding.inflate(layoutInflater)
            val view = categoryDetailsBinding.root
            setContentView(view)
            checkInternet()
        }

        override fun onStart() {
            super.onStart()
            checkInternet()
        }

        private fun checkInternet()  {
            if (isNetworkAvailable()) {
                setUI()
            } else {
                Toast.makeText(this, getString(R.string.str_checkinternet), Toast.LENGTH_SHORT).show()
            }
        }

        private fun isNetworkAvailable(): Boolean {
            val networkConnection = NetworkConnection()
            return networkConnection.isNetworkAvailable(this)
        }

    private fun setUI() {
             val imageView = findViewById<ImageView>(R.id.imageView)
             val strCategoryThumb = intent.extras?.getString("strCategoryThumb")
             val strCategoryDescription = intent.extras?.getString("strCategoryDescription")
             val strCategory = intent.extras?.getString("strCategory")
             categoryDetailsBinding.llContent.tvCategoryName.text=strCategory
             categoryDetailsBinding.llContent.tvCategoryDescription.text=strCategoryDescription
             Glide.with(this).load(strCategoryThumb).into(imageView)
         }

}




