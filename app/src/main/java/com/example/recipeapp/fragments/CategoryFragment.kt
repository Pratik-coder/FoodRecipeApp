package com.example.recipeapp.fragments

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.adapter.CategoryAdapter
import com.example.recipeapp.application.RecipeApplication
import com.example.recipeapp.databinding.FragmentCategoryBinding
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.viewmodel.CategoryViewModel
import com.example.recipeapp.viewmodel.CategoryViewModelFactory


class CategoryFragment : Fragment()
{
   private lateinit var binding: FragmentCategoryBinding
   private lateinit var categoryAdapter: CategoryAdapter
   private lateinit var viewModel: CategoryViewModel
   private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCategoryBinding.inflate(inflater)
        val view=binding.root
        val categoryRepository=CategoryRepository()
        val categoryViewModelFactory=CategoryViewModelFactory(requireActivity().application,categoryRepository)
        viewModel=ViewModelProvider(this, categoryViewModelFactory)[CategoryViewModel::class.java]
        viewModel=(activity as MainActivity).categoryViewModel
        progressDialog=ProgressDialog(requireContext())
        viewModel.getCategories()

        setUpRecyclerView()

        viewModel.categoryList.observe(viewLifecycleOwner, Observer {
            response->
            when(response)
            {
                is com.example.recipeapp.model.Result.Loading->
                {
                    progressDialog.setMessage("Loading...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                }

                is com.example.recipeapp.model.Result.Success->
                {
                    progressDialog.dismiss()
                    response.data?.let {
                        categoryAdapter.differ.submitList(it.categories?.toList())
                    }
                }

                is com.example.recipeapp.model.Result.Error->
                {
                    progressDialog.dismiss()
                    response.message?.let {
                        Toast.makeText(requireContext(),"An Error Occurred",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        )

      //  return inflater.inflate(R.layout.fragment_category, container, false)
        return view
    }


    private fun setUpRecyclerView()
    {
        categoryAdapter= CategoryAdapter()
        binding.rvcategory.apply {
        adapter=categoryAdapter
        layoutManager=LinearLayoutManager(requireContext())
        }
    }


    companion object
    {
        fun newInstance()=CategoryFragment()
    }
}