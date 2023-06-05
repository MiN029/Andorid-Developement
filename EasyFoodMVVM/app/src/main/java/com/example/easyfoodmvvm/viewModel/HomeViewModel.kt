package com.example.easyfoodmvvm.viewModel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfoodmvvm.databinding.FragmentHomeBinding
import com.example.easyfoodmvvm.db.MealDatabase
import com.example.easyfoodmvvm.db.MealDoa
import com.example.easyfoodmvvm.pojo.*
import com.example.easyfoodmvvm.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (private var mealDatabase: MealDatabase): ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealByCategory>>()
    private var categoryListLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeal()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : retrofit2.Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment" , t.message.toString())
            }

        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getMealByCategory("Seafood").enqueue(object : Callback<MealByCategoryList>{
            override fun onResponse(
                call: Call<MealByCategoryList>,
                response: Response<MealByCategoryList>
            ) {
                response.body()?.let {MealByCategoryList ->
                    popularItemsLiveData.postValue(MealByCategoryList.meals)
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.e("HomeViewModel" , t.message.toString())
            }

        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategoryList().enqueue( object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {CategoryList ->
                    categoryListLiveData.postValue(CategoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel" , t.message.toString())
            }

        })
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().insert(meal)
        }
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun observeRandomMealLiveData() : LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData() : LiveData<List<MealByCategory>>{
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData() : LiveData<List<Category>>{
        return categoryListLiveData
    }

    fun observerFavoritesMealsLiveData () : LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }
}
