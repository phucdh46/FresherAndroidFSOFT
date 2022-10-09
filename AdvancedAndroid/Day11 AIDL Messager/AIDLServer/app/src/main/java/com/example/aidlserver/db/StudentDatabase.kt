package com.example.aidlserver.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aidlserver.Student

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        private var instance: StudentDatabase? = null

        //singleton pattern
        @Synchronized
        fun getInstance(ctx: Context): StudentDatabase {
            if (instance == null) {
                //data/data/package/database/student.db
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    StudentDatabase::class.java,
                    "student.db"
                ).allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}