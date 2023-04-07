package com.example.easyfoodmvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.easyfoodmvvm.databinding.FragmentCatagoryBinding
import com.example.easyfoodmvvm.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private lateinit var binding : FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater , container ,false)
        val view = this.binding.root
        return view
    }

}