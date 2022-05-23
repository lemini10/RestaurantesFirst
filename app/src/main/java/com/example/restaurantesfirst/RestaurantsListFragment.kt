package com.example.restaurantesfirst

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantesfirst.databinding.FragmentLoginBinding
import com.example.restaurantesfirst.databinding.FragmentRestaurantsListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RestaurantsListFragment : Fragment(), RestaurantHandler {

    private var _binding : FragmentRestaurantsListBinding? = null
    private val binding get() = _binding!!
    private var managerLayout: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    var restaurantsList: List<Restaurant>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fetchRestaurantList()
        _binding = FragmentRestaurantsListBinding.inflate(inflater, container,false)
        val root: View = binding.root
        return root
    }

    private fun setUpRecycler(restaurants: List<Restaurant>) {
        managerLayout = LinearLayoutManager(this.context)
        val recyclerView = binding.restaurantsRecycler
        recyclerView.layoutManager = managerLayout
        adapter = RecyclerAdapter(this,restaurants)
        recyclerView.adapter = adapter
    }

    private fun fetchRestaurantList() {
        CoroutineScope(Dispatchers.IO).launch {
            val request: Response<Restaurants> = RetrofitProvider.getRetrofit().create(APIService::class.java).getRestaurants("getRestaurants")
            val restaurantsResponse: Restaurants? = request.body()
            activity?.runOnUiThread {
                if (request.isSuccessful && restaurantsResponse != null) {
                    restaurantsList = restaurantsResponse.restaurants
                    setUpRecycler(restaurantsResponse.restaurants)
                } else {
                    Toast.makeText(context, "Couldn't fetch the restaurants", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun restaurantSelected(position: Int) {
        Navigation.findNavController(binding.root).navigate(R.id.action_restaurantsListFragment_to_restaurantDetailFragment, Bundle().apply {
            val safeRestaurants: List<Restaurant> = restaurantsList ?: listOf()
            Log.e("----ERROR------", safeRestaurants[position].toString())
            if (safeRestaurants.isNotEmpty()) {
                putString("name", safeRestaurants[position].name.toString())
                putString("address", safeRestaurants[position].address.toString())
                putString("review", safeRestaurants[position].reviews.toString())
                putString("image1", safeRestaurants[position].photograph.first().toString())
                putString("image2", safeRestaurants[position].photograph[1].toString())
                putString("image3", safeRestaurants[position].photograph[2].toString())
                putString("foundationYear", safeRestaurants[position].foundationYear.toString())
                putString("averagePrice", safeRestaurants[position].price.toString().toString())
                putString("score", safeRestaurants[position].rating.toString())
            }
        })
    }
}