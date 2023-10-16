package com.example.recipeapp.fragments

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
import com.bumptech.glide.Glide
import com.example.recipeapp.MainActivity
import com.example.recipeapp.R
import com.example.recipeapp.application.RecipeApplication
import com.example.recipeapp.databinding.FragmentRandomBinding
import com.example.recipeapp.model.MealData
import com.example.recipeapp.repository.CategoryRepository
import com.example.recipeapp.viewmodel.CategoryViewModel
import com.example.recipeapp.viewmodel.CategoryViewModelFactory

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [RandomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RandomFragment : Fragment() {

    private lateinit var binding: FragmentRandomBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var progressDialog:ProgressDialog



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentRandomBinding.inflate(inflater)
        val view=binding.root
        val repository=CategoryRepository()
        val viewModelFactory=CategoryViewModelFactory(requireActivity().application,repository)
        viewModel=ViewModelProvider(this,viewModelFactory)[CategoryViewModel::class.java]
        viewModel=(activity as MainActivity).categoryViewModel
        progressDialog=ProgressDialog(requireContext())
        viewModel.getRandomRecipes()

        viewModel.randomRecipe.observe(viewLifecycleOwner, Observer {
            response->
            when(response)
            {
                is com.example.recipeapp.model.Result.Loading->
                {
                       // progressDialog=ProgressDialog(requireContext())
                        progressDialog.setMessage("Loading...")
                        progressDialog.setCancelable(false)
                        progressDialog.show()

                }

                is com.example.recipeapp.model.Result.Success->
                {
                  //  progressDialog=ProgressDialog(requireContext())
                    progressDialog.dismiss()
                    response.data?.let {
                        val recipe=it.meals?.get(0)
                        setRecipeData(recipe)
                    }
                }

                is com.example.recipeapp.model.Result.Error->
                {
                  //  progressDialog=ProgressDialog(requireContext())
                    progressDialog.dismiss()
                    response.message?.let {
                        Toast.makeText(requireContext(),"An Error Occurred",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return view
    }

    private fun setRecipeData(mealData: MealData?)
    {
        Glide.with(binding.root).load(mealData?.strMealThumb).into(binding.mealImage)
        binding.mealText.text=mealData?.strMeal
        binding.recipeIngredient.text=mealData?.let { viewModel .getIngredients(mealData)}
        binding.recipeInstruction.text=mealData?.strInstructions
    }
}