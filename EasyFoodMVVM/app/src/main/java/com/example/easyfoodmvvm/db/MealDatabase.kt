package com.example.easyfoodmvvm.db

import android.content.Context
import androidx.room.*
import com.example.easyfoodmvvm.pojo.Meal
import com.example.easyfoodmvvm.pojo.MealByCategory

@Database(entities = [Meal::class] , version = 1 , exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao() : MealDoa

    companion object{
        @Volatile
        var INSTANCE : MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : MealDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "Meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }
}