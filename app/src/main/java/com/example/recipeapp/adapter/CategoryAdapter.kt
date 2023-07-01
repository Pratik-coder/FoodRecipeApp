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



class CategoryAdapter (private var context: Context,private val categoryList:List<CategoryData>):RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{
    private lateinit var onItemClickListener:OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding=LayoutCategorylistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.onBind(getItem(position))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }


    interface OnItemClickListener
    {
        fun onCategoryClick(categoryData: CategoryData)
    }

    fun setOnCategoryClickedListener(listener: OnItemClickListener)
    {
          this.onItemClickListener=listener
    }

    private fun getItem(position: Int):CategoryData=categoryList[position]

   inner class ViewHolder(private val binding: LayoutCategorylistBinding,val listener:OnItemClickListener):
            RecyclerView.ViewHolder(binding.root)
   {
       fun onBind(categoryData: CategoryData) {
           binding.tvCategorylist.text=categoryData.strCategory
           val imageUrl=categoryData.strCategoryThumb
           Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_launcher_background).into(binding.ivCategory)
           itemView.setOnClickListener {
               listener.onCategoryClick(categoryData)
           }
       }
   }
}





