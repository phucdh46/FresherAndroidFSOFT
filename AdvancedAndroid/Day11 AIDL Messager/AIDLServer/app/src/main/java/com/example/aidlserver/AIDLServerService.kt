package com.example.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.aidlserver.db.StudentDao
import com.example.aidlserver.db.StudentDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AIDLServerService : Service() {
    private var listStudent = listOf<Student>()
    private lateinit var studentDao: StudentDao
    override fun onBind(intent: Intent): IBinder {
        //create student dao
        CoroutineScope(Dispatchers.IO).launch {
            studentDao =
                StudentDatabase.getInstance(applicationContext).studentDao()
        }
        return binder
    }

    private val binder: IAIDLServerInterface.Stub = object : IAIDLServerInterface.Stub() {
        //get all students
        override fun getAllStudent(): List<Student> {
            return studentDao.getAllStudents()
        }

        //create student
        override fun createStudent(student: Student?): Boolean {
            studentDao.insertStudent(student!!)
            return true
        }

        //get a student by name
        override fun getStudentByName(name: String): Student? {
            return studentDao.getStudentByName(name)
        }

        //update student
        override fun updateStudent(student: Student): Boolean {
            studentDao.updateStudent(student)
            return true
        }

        //find best average student
        override fun findBestAverage(): MutableList<Student>? {
            listStudent = studentDao.getAllStudents()
            return findBest(listStudent).toMutableList()
        }

        //fing worst average  student
        override fun findWorstAverage(): MutableList<Student> {
            listStudent = studentDao.getAllStudents()
            return findWorst(listStudent).toMutableList()
        }
    }

    private fun findBest(listStudent: List<Student>): List<Student> {
        var max = 0f
        var studentMax = arrayListOf<Student>()
        listStudent.forEach {
            val avg = (it.mathScore + it.physicScore + it.englistScore) / 3f
            if (avg > max) {
                studentMax.clear()
                max = avg
                studentMax.add(it)
            } else if (avg == max) {
                studentMax.add(it)
            }
        }
        Log.d("DHP", "max:${studentMax.toString()}")
        return studentMax
    }

    private fun findWorst(listStudent: List<Student>): List<Student> {
        var min =
            (listStudent[0].mathScore + listStudent[0].physicScore + listStudent[0].englistScore) / 3f
        var studentMin = arrayListOf<Student>()
        listStudent.forEach {
            val avg = (it.mathScore + it.physicScore + it.englistScore) / 3f
            if (avg < min) {
                studentMin.clear()
                min = avg
                studentMin.add(it)
            } else if (avg == min) {
                studentMin.add(it)
            }
        }
        Log.d("DHP", "min:${studentMin.toString()}")
        return studentMin
    }
}