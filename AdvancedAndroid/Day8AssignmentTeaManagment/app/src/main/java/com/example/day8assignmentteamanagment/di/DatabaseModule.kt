package com.example.day8assignmentteamanagment.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.day8assignmentteamanagment.db.TeaDao
import com.example.day8assignmentteamanagment.db.TeaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideTeaDatabase(app: Application): TeaDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            TeaDatabase::class.java,
            "tea.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTeaDao(db: TeaDatabase): TeaDao {
        return db.teaDao()
    }

    @Provides
    @Singleton
    fun provideSharePreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
    }
}