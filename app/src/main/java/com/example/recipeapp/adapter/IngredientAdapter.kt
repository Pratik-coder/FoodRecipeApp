package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.LayoutCategorylistBinding
import com.example.recipeapp.databinding.LayoutIngredientlistBinding
import com.example.recipeapp.model.IngredientData

class IngredientAdapter:RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientAdapter.IngredientViewHolder{

        val binding= LayoutIngredientlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int)
    {
        holder.onBindData(differList.currentList[position])
    }


    override fun getItemCount(): Int
    {
         return differList.currentList.size
    }

    inner class IngredientViewHolder(val binding: LayoutIngredientlistBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun onBindData(ingredientData: IngredientData)
        {
            binding.tvIngredientName.text=ingredientData.strIngredient
        }
    }

    val differCallBack=object :DiffUtil.ItemCallback<IngredientData>()
    {
        override fun areItemsTheSame(oldItem: IngredientData, newItem: IngredientData): Boolean {
              return oldItem.idIngredient==newItem.idIngredient
        }

        override fun areContentsTheSame(oldItem: IngredientData, newItem: IngredientData): Boolean {
           return oldItem.idIngredient==newItem.idIngredient
        }
    }

    val differList=AsyncListDiffer(this,differCallBack)
}