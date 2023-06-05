package com.example.easyfoodmvvm.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfoodmvvm.HomeFragment
import com.example.easyfoodmvvm.R
import com.example.easyfoodmvvm.databinding.ActivityMealBinding
import com.example.easyfoodmvvm.db.MealDatabase
import com.example.easyfoodmvvm.pojo.Meal
import com.example.easyfoodmvvm.viewModel.MealViewModel
import com.example.easyfoodmvvm.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealImg : String
    private lateinit var mMealViewModel: MealViewModel
    private lateinit var youtubeLink : String
    private var mealToSave : Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)

        mMealViewModel = ViewModelProvider(this@MealActivity,viewModelFactory)[MealViewModel::class.java]

        getInfomationOfMeal()
        setInformationInImage()
        loadingCase()


        mMealViewModel.getMealDetails(mealId)
        observerMealdetailsLiveData()

        onYoutubeImgClick()
        onFavriteClick()
    }

    private fun onFavriteClick() {
        binding.btnFav.setOnClickListener {
            mealToSave?.let {
                mMealViewModel.insertMeal(it)
                Toast.makeText(this, "Saved to your favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImgClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealdetailsLiveData() {

        mMealViewModel.observerMealDetailsLiveData().observe(this, object : Observer<Meal>{
            @SuppressLint("SetTextI18n")
            override fun onChanged(value: Meal) {
                val meal = value
                onResponseCase()

                mealToSave = meal

                binding.txtCatagories.text = "Category : ${meal.strCategory}"
                binding.txtLocations.text = "Location : ${meal.strArea}"
                binding.txtInstructionDetail.text = meal.strInstructions
                youtubeLink = meal.strYoutube.toString()
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

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnFav.visibility = View.INVISIBLE
        binding.txtInstruction.visibility = View.INVISIBLE
        binding.txtLocations.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
        binding.txtLocations.visibility = View.INVISIBLE

    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnFav.visibility = View.VISIBLE
        binding.txtInstruction.visibility = View.VISIBLE
        binding.txtLocations.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
        binding.txtLocations.visibility = View.VISIBLE
    }

}