package com.example.restaurantesfirst

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
                    setUpRecycler(restaurantsResponse.restaurants)
                } else {
                    Toast.makeText(context, "Couldn't fetch the restaurants", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun restaurantSelected(position: Int) {

    }
}