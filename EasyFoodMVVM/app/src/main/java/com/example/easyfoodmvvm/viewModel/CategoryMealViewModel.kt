package com.example.easyfoodmvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfoodmvvm.pojo.MealByCategory
import com.example.easyfoodmvvm.pojo.MealByCategoryList
import com.example.easyfoodmvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class CategoryMealViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealByCategory>>()

    fun getMealsByCategory(categoryName : String){
        RetrofitInstance.api.getMealByCategory(categoryName).enqueue(object : Callback<MealByCategoryList>{
            override fun onResponse(
                call: Call<MealByCategoryList>,
                response: Response<MealByCategoryList>
            ) {
                response.body()?.let { mealsList ->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }
            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.e("CategoryMealViewMode" , t.message.toString())
            }

        })
    }
    fun observerMealsByCategory() : LiveData<List<MealByCategory>>{
        return mealsLiveData
    }
}