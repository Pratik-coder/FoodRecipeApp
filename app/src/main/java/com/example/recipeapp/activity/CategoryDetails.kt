
package com.example.recipeapp.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide

import com.example.recipeapp.databinding.ActivityCategoryDetailsBinding
import com.example.recipeapp.model.CategoryData


class CategoryDetails : AppCompatActivity() {
    private lateinit var categoryDetailsBinding: ActivityCategoryDetailsBinding


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            categoryDetailsBinding= ActivityCategoryDetailsBinding.inflate(layoutInflater)
            val view=categoryDetailsBinding.root
            setContentView(view)

        }



        private fun setCategoryDetails(categoryData: CategoryData)
        {
               Glide.with(this@CategoryDetails).load(categoryData.strCategoryThumb).into(categoryDetailsBinding.ivCategoryImage)
               categoryDetailsBinding.tvCategoryName.text=categoryData.strCategory
               categoryDetailsBinding.tvCategoryDescription.text=categoryData.strCategoryDescription
        }
}




