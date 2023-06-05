package com.example.easyfoodmvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfoodmvvm.db.MealDatabase

class HomeViewModelFactory(
    val mealDatabase: MealDatabase
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }
}