package com.example.restaurantesfirst

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

class ValidUsers (
    @SerializedName("users")  var users: List<User>
        )

class User (
    @SerializedName("mail")  var mail: String,
    @SerializedName("password") var password: String)

class Restaurants (
    @SerializedName("restaurants")  var restaurants: List<Restaurant>
    )

class Restaurant (
    @SerializedName("id")  var id: String,
    @SerializedName("name")  var name: String,
    @SerializedName("photograph")  var photograph: List<String>,
    @SerializedName("address")  var address: String,
    @SerializedName("anioCreate")  var foundationYear: Int,
    @SerializedName("price")  var price: String,
    @SerializedName("rating")  var rating: Int,
    @SerializedName("reviews")  var reviews: String,
    )

interface APIService {
    @GET
    suspend fun getRestaurants(@Url url: String): Response<Restaurants>
    @GET
    suspend fun getUsers(@Url url: String): Response<ValidUsers>
}