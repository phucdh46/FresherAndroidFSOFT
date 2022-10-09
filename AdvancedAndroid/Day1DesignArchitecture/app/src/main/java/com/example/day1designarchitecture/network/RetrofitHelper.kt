package com.example.day1designarchitecture.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val baseUrl = "https://run.mocky.io/"
    //Prevent leak memory occur user singleton
    private val retrofit: Retrofit?= null
    fun getInstance(): Retrofit {
        if (retrofit==null){
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }else{
            return retrofit
        }
    }
}