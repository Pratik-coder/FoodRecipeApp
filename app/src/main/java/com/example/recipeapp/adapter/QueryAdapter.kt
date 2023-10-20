package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.LayoutIngredientlistBinding
import com.example.recipeapp.databinding.LayoutQuerylistBinding
import com.example.recipeapp.model.MealData

class QueryAdapter:RecyclerView.Adapter<QueryAdapter.QueryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryAdapter.QueryViewHolder{

        val binding= LayoutQuerylistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QueryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QueryAdapter.QueryViewHolder, position: Int)
    {
        holder.onBindData(differList.currentList[position])
    }


    override fun getItemCount(): Int
    {
      return differList.currentList.size
    }

    inner class QueryViewHolder(private val binding: LayoutQuerylistBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun onBindData(mealData: MealData)
        {
            Glide.with(binding.root).load(mealData.strMealThumb).into(binding.ivFilter)
            binding.tvFilter.text=mealData.strMeal
            binding.cardView.setOnClickListener {
                onItemClickListener?.let {
                    it(mealData)
                }
            }
        }
    }

    private var onItemClickListener:((MealData)->Unit)?=null

    fun setOnItemClickListener(listener:(MealData)->Unit)
    {
        onItemClickListener=listener
    }

    val differCallBackQueryList=object :DiffUtil.ItemCallback<MealData>()
    {
        override fun areItemsTheSame(oldItem: MealData, newItem: MealData): Boolean {
           return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealData, newItem: MealData): Boolean {
           return oldItem==newItem
        }
    }

    val differList=AsyncListDiffer(this,differCallBackQueryList)
}

