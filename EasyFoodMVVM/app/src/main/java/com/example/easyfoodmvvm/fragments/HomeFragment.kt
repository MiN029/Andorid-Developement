package com.example.easyfoodmvvm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfoodmvvm.databinding.FragmentHomeBinding
import com.example.easyfoodmvvm.pojo.Meal
import com.example.easyfoodmvvm.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mHomeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHomeViewModel = ViewModelProvider(this@HomeFragment).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater , container ,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mHomeViewModel.getRandomMeal()
        observeRandomMealLiveData()
        onRandomMealClikc()
    }

    private fun onRandomMealClikc() {
        binding.imgRadomMeal.setOnClickListener {
            val intent : ()
        }
    }

    private fun observeRandomMealLiveData() {
        mHomeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner , object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding.imgRadomMeal)
            }

        })
    }
}