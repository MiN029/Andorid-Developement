package com.example.easyfoodmvvm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfoodmvvm.activity.CategoryMealActivity
import com.example.easyfoodmvvm.activity.MainActivity
import com.example.easyfoodmvvm.activity.MealActivity
import com.example.easyfoodmvvm.adapter.CategoryAdapter
import com.example.easyfoodmvvm.adapter.MostPopularAdapter
import com.example.easyfoodmvvm.databinding.FragmentHomeBinding
import com.example.easyfoodmvvm.pojo.Category
import com.example.easyfoodmvvm.pojo.Meal
import com.example.easyfoodmvvm.pojo.MealByCategory
import com.example.easyfoodmvvm.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var popularAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    companion object{
        const val  MEAL_ID = "com.example.easyfoodmvvm.idMeal"
        const val  MEAL_NAME = "com.example.easyfoodmvvm.idName"
        const val  MEAL_THUMB = "com.example.easyfoodmvvm.thumbMeal"
        const val  CATEGORY_NAME = "com.example.easyfoodmvvm.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater , container ,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerViews()
        prepareCategoryItemsRecyclerViews()

        viewModel.getRandomMeal()
        observeRandomMealLiveData()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()

        onPopularItemClick()

        viewModel.getCategories()
        observeCategoryLiveData()

        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = {category ->
            val intent = Intent(activity,CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoryItemsRecyclerViews() {
        binding.rvCatagories.apply {
            categoryAdapter = CategoryAdapter()
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoryAdapter
        }
    }

    private fun observeCategoryLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { CategoryList ->
                categoryAdapter.setCategoryList(categoryList = CategoryList as ArrayList<Category>)
        })
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick = {meals ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meals.idMeal)
            intent.putExtra(MEAL_NAME,meals.strMeal)
            intent.putExtra(MEAL_THUMB,meals.strMealThumb)
            startActivity(intent)

        }
    }

    private fun preparePopularItemsRecyclerViews() {
        binding.rvPopularItems.apply {
            popularAdapter = MostPopularAdapter()
            layoutManager = LinearLayoutManager(activity , LinearLayoutManager.HORIZONTAL , false)
            adapter = popularAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularAdapter.setMeals(mealList = mealList as ArrayList<MealByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.imgRadomMeal.setOnClickListener {
            val intent = Intent(activity , MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMealLiveData() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner ,
        { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRadomMeal)
            this.randomMeal = meal
        })
    }
}