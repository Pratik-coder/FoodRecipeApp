package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.LayoutArealistBinding
import com.example.recipeapp.databinding.LayoutIngredientlistBinding
import com.example.recipeapp.model.AreaData

class AreaAdapter:RecyclerView.Adapter<AreaAdapter.AreaViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaAdapter.AreaViewHolder{

        val binding= LayoutArealistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AreaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
     holder.onBindData(differList.currentList[position])
    }


    override fun getItemCount(): Int
    {
       return differList.currentList.size
    }

    inner class AreaViewHolder(val binding: LayoutArealistBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun onBindData(areaData: AreaData)
        {
            binding.tvArea.text=areaData.strArea
        }
    }

    val differCallBack=object :DiffUtil.ItemCallback<AreaData>()
    {
        override fun areItemsTheSame(oldItem: AreaData, newItem: AreaData): Boolean {
              return oldItem.strArea==newItem.strArea
        }

        override fun areContentsTheSame(oldItem: AreaData, newItem: AreaData): Boolean {
            return oldItem.strArea==newItem.strArea
        }
    }

    val differList=AsyncListDiffer(this,differCallBack)
}