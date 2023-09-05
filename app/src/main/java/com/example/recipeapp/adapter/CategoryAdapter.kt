package com.example.recipeapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.`interface`.OnCategoryClickedListener
import com.example.recipeapp.databinding.LayoutCategorylistBinding
import com.example.recipeapp.model.CategoryData



class CategoryAdapter(private var context: Context,private val categoryList:List<CategoryData>,private val onItemClickListener:OnItemClickListener):RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding=LayoutCategorylistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.onBind(getItem(position),onItemClickListener)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    interface OnItemClickListener
    {
        fun onCategoryClick(categoryData:CategoryData)
    }

    private fun getItem(position: Int):CategoryData=categoryList[position]

   inner class ViewHolder(private val binding: LayoutCategorylistBinding):
            RecyclerView.ViewHolder(binding.root)
   {
       fun onBind(categoryData: CategoryData,listener:OnItemClickListener) {
           binding.tvCategorylist.text=categoryData.strCategory
           val imageUrl=categoryData.strCategoryThumb
           Glide.with(context).load(imageUrl).into(binding.ivCategory)
           itemView.setOnClickListener {
               listener.onCategoryClick(categoryData)
           }
       }
   }
}





