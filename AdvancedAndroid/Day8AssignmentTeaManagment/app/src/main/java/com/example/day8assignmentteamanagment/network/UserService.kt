package com.example.day8assignmentteamanagment.network

import retrofit2.http.GET

interface UserService {
    @GET("user")
    suspend fun getUser(): UserResponse
}