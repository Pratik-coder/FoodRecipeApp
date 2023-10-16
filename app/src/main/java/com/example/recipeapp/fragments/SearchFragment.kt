package com.example.recipeapp.fragments

import android.app.Application
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.adapter.FilterAdapter
import com.example.recipeapp.application.RecipeApplication
import com.example.recipeapp.constants.Constant
import com.example.recipeapp.constants.Constant.SEARCH
import com.example.recipeapp.databinding.FragmentSearchBinding
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.viewmodel.CategoryViewModel
import com.example.recipeapp.viewmodel.CategoryViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var progressDialog:ProgressDialog





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater)
        val view=binding.root
        val repository=CategoryRepository()
        val viewModelFactory=CategoryViewModelFactory(requireActivity().application,repository)
        viewModel=ViewModelProvider(this,viewModelFactory)[CategoryViewModel::class.java]
        viewModel=(activity as MainActivity).categoryViewModel
        progressDialog=ProgressDialog(requireContext())
        setUpRecyclerView()
        var job:Job?=null

       binding.etSearchMeal.addTextChangedListener {
          job?.cancel()
          job= MainScope().launch {
              delay(SEARCH)
              it?.let {
                  if (it.toString().isNotEmpty())
                  {
                      binding.searchImageMeal.visibility=View.INVISIBLE
                      binding.rvSearchMeal.visibility=View.VISIBLE
                      viewModel.getRecipeBySearch(it.toString())
                  }
              }
          }
       }

        viewModel.searchRecipeList.observe(viewLifecycleOwner, Observer {
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
                        filterAdapter.differList.submitList(it.meals?.toList())
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
        })
        return view
    }

    private fun setUpRecyclerView()
    {
        filterAdapter= FilterAdapter()
        binding.rvSearchMeal.apply {
            adapter=filterAdapter
            layoutManager=LinearLayoutManager(requireContext())
        }
    }
}