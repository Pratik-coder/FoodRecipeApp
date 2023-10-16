package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.LayoutCategorylistBinding
import com.example.recipeapp.model.CategoryData



class CategoryAdapter:RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding=LayoutCategorylistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
         holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }


   inner class ViewHolder(private val binding: LayoutCategorylistBinding):
            RecyclerView.ViewHolder(binding.root)
   {
       fun onBind(categoryData: CategoryData) {
           binding.tvCategorylist.text= categoryData.strCategory!!
           val imageUrl=categoryData.strCategoryThumb!!
           Glide.with(binding.root).load(imageUrl).into(binding.ivCategory)
           binding.cardView.setOnClickListener {
               onItemClickListener?.let {it(categoryData)}
           }
       }
   }

    private var onItemClickListener:((CategoryData)->Unit)?=null

    fun setOnItemClickListener(listener:(CategoryData)->Unit)
    {
        onItemClickListener=listener
    }

    private val differCallback=object : DiffUtil.ItemCallback<CategoryData>()
    {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem.idCategory==newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
               return oldItem==newItem
        }
    }

    val differ=AsyncListDiffer(this,differCallback)
}





