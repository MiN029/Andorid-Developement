package com.example.easyfoodmvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfoodmvvm.databinding.ActivityMealBinding
import com.example.easyfoodmvvm.databinding.PopularItemsBinding
import com.example.easyfoodmvvm.pojo.Category
import com.example.easyfoodmvvm.pojo.MealByCategory

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    class PopularMealViewHolder (var binding: PopularItemsBinding): RecyclerView.ViewHolder(binding.root)

    private var mealList = ArrayList<MealByCategory>()
    lateinit var onItemClick : ((MealByCategory) -> Unit)

    fun setMeals(mealList:ArrayList<MealByCategory>){
        this.mealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent , false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

}