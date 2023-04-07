package com.example.easyfoodmvvm.retrofit

import com.example.easyfoodmvvm.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>

}