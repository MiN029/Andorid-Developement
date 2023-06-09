package com.example.easyfoodmvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.easyfoodmvvm.pojo.Meal

@Dao
interface MealDoa {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeal() : LiveData<List<Meal>>

}