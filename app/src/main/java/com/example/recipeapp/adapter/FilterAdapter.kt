package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.LayoutCategorylistBinding
import com.example.recipeapp.databinding.LayoutSearchlistBinding
import com.example.recipeapp.model.MealData

class FilterAdapter:RecyclerView.Adapter<FilterAdapter.FilterViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.FilterViewHolder {

        val binding= LayoutSearchlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterAdapter.FilterViewHolder, position: Int)
    {
        holder.onBindData(differList.currentList[position])
    }

    override fun getItemCount(): Int {
       return differList.currentList.size
    }

    inner class FilterViewHolder(val binding: LayoutSearchlistBinding):RecyclerView.ViewHolder(binding.root)
    {
          fun onBindData(mealData: MealData)
          {
              Glide.with(binding.root).load(mealData.strMealThumb).into(binding.ivSearchCategory)
              binding.tvSearchCategoryName.text=mealData.strMeal
          }
    }

    private val differCallBack=object :DiffUtil.ItemCallback<MealData>()
    {
        override fun areItemsTheSame(oldItem: MealData, newItem: MealData): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealData, newItem: MealData): Boolean {
           return oldItem.idMeal==newItem.idMeal
        }
    }

    val differList=AsyncListDiffer(this,differCallBack)
}