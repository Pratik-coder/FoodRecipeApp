package com.example.recipeapp.fragments

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
import com.example.recipeapp.adapter.AreaAdapter
import com.example.recipeapp.databinding.FragmentAreaBinding
import com.example.recipeapp.databinding.FragmentRandomBinding
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.viewmodel.CategoryViewModel
import com.example.recipeapp.viewmodel.CategoryViewModelFactory


class AreaFragment : Fragment() {

private lateinit var binding: FragmentAreaBinding
private lateinit var viewModel: CategoryViewModel
private lateinit var areaAdapter: AreaAdapter
private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAreaBinding.inflate(inflater)
        val view=binding.root
        progressDialog=ProgressDialog(requireContext())
        val repository=CategoryRepository()
        val viewModelFactory=CategoryViewModelFactory(requireActivity().application,repository)
        viewModel=ViewModelProvider(this,viewModelFactory)[CategoryViewModel::class.java]
        viewModel=(activity as MainActivity).categoryViewModel

        viewModel.getAllAreaList()
        setUpRecyclerView()

        viewModel.areaList.observe(viewLifecycleOwner, Observer {
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
                        areaAdapter.differList.submitList(it.meals.toList())
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
        areaAdapter= AreaAdapter()
        binding.rvArea.apply {
            adapter=areaAdapter
            layoutManager=LinearLayoutManager(requireContext())
        }
    }
}