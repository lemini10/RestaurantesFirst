package com.example.restaurantesfirst

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdapter(val movieHandler: RestaurantHandler, val restaurants: List<Restaurant>?): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {

        val currentRestaurant: Restaurant? = restaurants?.get(position)
        if (currentRestaurant != null) {
            holder.nameText.text = currentRestaurant.name
            holder.averagePriceTextview.text = currentRestaurant.price
            holder.foundationYearTextView.text = currentRestaurant.foundationYear.toString()
            holder.scoreTextView.text = currentRestaurant.rating.toString()
            Picasso.get().load(currentRestaurant.photograph.first()).fit().centerInside().into(holder.restaurantImage)
        }
    }

    override fun getItemCount(): Int {
        return restaurants?.size ?: 0
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameText = itemView.findViewById<TextView>(R.id.nameTextItem)
        val scoreTextView = itemView.findViewById<TextView>(R.id.scoreTextItem)
        val averagePriceTextview = itemView.findViewById<TextView>(R.id.averagePriceTextItem)
        val foundationYearTextView = itemView.findViewById<TextView>(R.id.foundationYearTextItem)
        val restaurantImage: ImageView = itemView.findViewById(R.id.restaurantImageView)

        init {
            itemView.setOnClickListener{
                val position: Int = adapterPosition
                movieHandler.restaurantSelected(position)
            }
        }

    }
}


interface RestaurantHandler {
    fun restaurantSelected(position: Int)
}