package com.example.aidlserver.db

import androidx.room.*
import com.example.aidlserver.Student

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Query("SELECT * FROM student_tb")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM student_tb WHERE name=:name")
    fun getStudentByName(name: String): Student
}