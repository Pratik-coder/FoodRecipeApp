
package com.example.recipeapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapter.CategoryAdapter
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.fragments.*
import com.example.recipeapp.model.CategoryData
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.viewmodel.CategoryViewModel
import com.example.recipeapp.viewmodel.CategoryViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity()
 {
     private lateinit var mainBinding: ActivityMainBinding
     lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_main)
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        val categoryRepository=CategoryRepository()
        val recipeViewModelFactory=CategoryViewModelFactory(application,categoryRepository)
        categoryViewModel=ViewModelProvider(this,recipeViewModelFactory)[CategoryViewModel::class.java]
        loadFragment(CategoryFragment.newInstance())
        getFragmentByName()
    }

     private fun loadFragment(fragment: Fragment)
     {
         supportFragmentManager.beginTransaction()
             .replace(R.id.frameLayout,fragment).commit()
     }


     private fun getFragmentByName()
     {
         mainBinding.bottomNavigationView.setOnItemSelectedListener {
                 item->
             var fragment= Fragment()
             when(item.itemId)
             {
                 R.id.nav_category->
                 {
                     fragment=CategoryFragment()
                     loadFragment(fragment)
                     true
                 }

                 R.id.nav_area->
                 {
                     fragment=AreaFragment()
                     loadFragment(fragment)
                     true
                 }

                 R.id.nav_random->
                 {
                     fragment=RandomFragment()
                     loadFragment(fragment)
                     true
                 }

                 R.id.nav_ingredient->
                 {
                     fragment=IngredientFragment()
                     loadFragment(fragment)
                     true
                 }

                 R.id.nav_search->
                 {
                     fragment=SearchFragment()
                     loadFragment(fragment)
                     true
                 }
                 else->false
             }
         }
     }
}

