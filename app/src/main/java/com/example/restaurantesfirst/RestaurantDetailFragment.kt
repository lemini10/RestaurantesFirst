package com.example.restaurantesfirst

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurantesfirst.databinding.FragmentRestaurantDetailBinding
import com.example.restaurantesfirst.databinding.FragmentRestaurantsListBinding
import com.squareup.picasso.Picasso


class RestaurantDetailFragment() : Fragment() {


    private var _binding : FragmentRestaurantDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val name = requireArguments().getString("name")
        val address = requireArguments().getString("address")
        val review = requireArguments().getString("review")
        val image1 = requireArguments().getString("image1")
        val image2 = requireArguments().getString("image2")
        val image3 = requireArguments().getString("image3")
        val foundationYear = requireArguments().getString("foundationYear")
        val averagePrice = requireArguments().getString("averagePrice")
        val score = requireArguments().getString("score")

        _binding = FragmentRestaurantDetailBinding.inflate(inflater, container,false)
        val root: View = binding.root
        Picasso.get().load(image1).fit().centerInside().into(binding.firstimageView)
        Picasso.get().load(image2).fit().centerInside().into(binding.imageView2)
        Picasso.get().load(image3).fit().centerInside().into(binding.imageView3)
        binding.nameTextViewDetail.text = "Name:" + name ?: ""
        binding.adressTextViewDetail.text = "Adress:" + address ?: ""
        binding.reviewTextViewDetail.text = "Review:" + review ?: ""
        binding.foundationTextViewDetail.text = "Foundation Year:" + foundationYear ?: ""
        binding.averageTextViewDetail.text = "Average Price:" + averagePrice + " USD"
        binding.scoreTextViewDetail.text = "Rating:" + score ?: ""
        return root
    }
}