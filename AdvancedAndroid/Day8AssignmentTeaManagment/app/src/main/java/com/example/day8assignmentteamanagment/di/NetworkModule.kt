package com.example.day8assignmentteamanagment.di

import com.example.day8assignmentteamanagment.network.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            //https://apimocha.com/phucdh11/user
            .baseUrl("https://apimocha.com/phucdh11/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePassengerService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}