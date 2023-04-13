package com.example.easyfoodmvvm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfoodmvvm.HomeFragment
import com.example.easyfoodmvvm.R
import com.example.easyfoodmvvm.databinding.ActivityMealBinding
import com.example.easyfoodmvvm.pojo.Meal
import com.example.easyfoodmvvm.viewModel.MealViewModel
import retrofit2.Callback

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealImg : String
    private lateinit var mMealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mMealViewModel = ViewModelProvider(this@MealActivity).get(MealViewModel::class.java)

        getInfomationOfMeal()
        setInformationInImage()

        mMealViewModel.getMealDetails(mealId)
        observerMealdetailsLiveData()
    }

    private fun observerMealdetailsLiveData() {

        mMealViewModel.observerMealDetailsLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                val meal = value

                binding.txtCatagories.text = "Category : ${meal.strCategory}"
                binding.txtLocations.text = "Location : ${meal.strArea}"
                binding.txtInstructionDetail.text = meal.strInstructions
            }

        })
    }

    private fun setInformationInImage() {
        Glide.with(applicationContext)
            .load(mealImg)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getInfomationOfMeal() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealImg = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }


}