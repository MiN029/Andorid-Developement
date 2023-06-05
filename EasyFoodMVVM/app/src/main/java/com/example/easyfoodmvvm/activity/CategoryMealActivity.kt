package com.example.easyfoodmvvm.activity

import android.content.Intent
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfoodmvvm.HomeFragment
import com.example.easyfoodmvvm.R
import com.example.easyfoodmvvm.adapter.CategoryMealAdapter
import com.example.easyfoodmvvm.databinding.ActivityCategoryMealBinding
import com.example.easyfoodmvvm.databinding.ActivityMealBinding
import com.example.easyfoodmvvm.pojo.MealByCategory
import com.example.easyfoodmvvm.viewModel.CategoryMealViewModel
import com.example.easyfoodmvvm.viewModel.HomeViewModel
import kotlin.math.log

class CategoryMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealBinding
    private lateinit var mCategoryMealViewModel : CategoryMealViewModel
    lateinit var categoryMealAdapter : CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mCategoryMealViewModel = ViewModelProvider(this@CategoryMealActivity).get(CategoryMealViewModel::class.java)

        mCategoryMealViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        mCategoryMealViewModel.observerMealsByCategory().observe(this, Observer {MealsList ->
            binding.tvCategoryCount.text = MealsList.size.toString()
            categoryMealAdapter.setMealList(MealsList)
        })

        prepareRecyclerView()

    }

    private fun prepareRecyclerView() {
        binding.rvMeals.apply {
            categoryMealAdapter = CategoryMealAdapter()
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealAdapter
        }
    }
}