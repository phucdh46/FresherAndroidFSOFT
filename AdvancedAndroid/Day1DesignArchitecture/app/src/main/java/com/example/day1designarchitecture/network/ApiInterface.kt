package com.example.day1designarchitecture.network

import com.example.day1designarchitecture.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    //https://run.mocky.io/v3/ecaf6139-b1b6-44ed-9cd2-651ed65c1105

    //get user từ API
    @GET("/v3/ecaf6139-b1b6-44ed-9cd2-651ed65c1105")
    fun getUsers(): Call<User>
}