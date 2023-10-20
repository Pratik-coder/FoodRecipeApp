package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.LayoutCategorylistBinding
import com.example.recipeapp.databinding.LayoutIngredientlistBinding
import com.example.recipeapp.model.IngredientData
import com.example.recipeapp.model.MealData

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
        fun onBindData(mealData: MealData)
        {

             if (mealData.strIngredient!=null)
             {
                 binding.tvIngredientName.text=mealData.strIngredient
             }

            if(mealData.strArea!=null)
            {
                binding.tvIngredientName.text=mealData.strArea
            }

            binding.cardViewIngredient.setOnClickListener {
                onItemClickListener?.let {it(mealData)}
            }
        }
    }

    private var onItemClickListener:((MealData)->Unit)?=null

    fun setOnItemClickListener(listener:(MealData)->Unit)
    {
        onItemClickListener=listener
    }


    val differCallBack=object :DiffUtil.ItemCallback<MealData>()
    {
        override fun areItemsTheSame(oldItem: MealData, newItem: MealData): Boolean {
              return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealData, newItem: MealData): Boolean {
           return oldItem==newItem
        }
    }

    val differList=AsyncListDiffer(this,differCallBack)
}