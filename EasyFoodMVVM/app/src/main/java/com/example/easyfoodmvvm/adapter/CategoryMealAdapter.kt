package com.example.easyfoodmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfoodmvvm.databinding.MealItemBinding
import com.example.easyfoodmvvm.pojo.MealByCategory

class CategoryMealAdapter : RecyclerView.Adapter<CategoryMealAdapter.CategoryMealsViewHolder>() {

    inner class CategoryMealsViewHolder(var binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var mealList = ArrayList<MealByCategory>()

    fun setMealList(mealList:List<MealByCategory>){
        this.mealList = mealList as ArrayList<MealByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = mealList[position].strMeal
    }

}