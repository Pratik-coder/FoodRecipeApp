package com.example.recipeapp.`interface`

import com.example.recipeapp.model.CategoryData

interface OnCategoryClickedListener
{
    fun onCategoryClicked(position:Int,categoryData: CategoryData)
}