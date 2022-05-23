package com.example.restaurantesfirst

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://demo0456954.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}