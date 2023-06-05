package com.example.easyfoodmvvm.retrofit

import com.example.easyfoodmvvm.pojo.CategoryList
import com.example.easyfoodmvvm.pojo.MealByCategory
import com.example.easyfoodmvvm.pojo.MealByCategoryList
import com.example.easyfoodmvvm.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>

    @GET("lookup.php?")
    fun getMealsDetails(@Query("i") id : String) : Call<MealList>

    @GET("filter.php?")
    fun getMealByCategory(@Query("c") categoryName : String) : Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategoryList() : Call<CategoryList>
}