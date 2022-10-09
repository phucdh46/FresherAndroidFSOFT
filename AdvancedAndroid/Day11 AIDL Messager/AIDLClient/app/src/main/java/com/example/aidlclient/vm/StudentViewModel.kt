package com.example.aidlclient.vm

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aidlserver.IAIDLServerInterface
import com.example.aidlserver.Student
import kotlinx.coroutines.launch

class StudentViewModel(app: Application) :
    AndroidViewModel(app) {
    private val TAG: String = "DHP"
    val _listStudents = MutableLiveData<ArrayList<Student>>()
    val listStudent: LiveData<ArrayList<Student>>
        get() = getListStudents()

    private var isConnect: Boolean = false
    private var iaidlServerInterface: IAIDLServerInterface? = null
    val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
            iaidlServerInterface = IAIDLServerInterface.Stub.asInterface(iBinder)
            isConnect = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isConnect = false
        }
    }
    val intent = Intent("AIDLServerService").setPackage("com.example.aidlserver")


    fun getListStudents(): MutableLiveData<ArrayList<Student>> {
        viewModelScope.launch {
            getApplication<Application>().applicationContext.bindService(
                intent,
                mConnection,
                AppCompatActivity.BIND_AUTO_CREATE
            )
            if (iaidlServerInterface != null) {
                _listStudents.value = iaidlServerInterface!!.allStudent as ArrayList<Student>
            } else {
                Log.d(TAG, "not connection")
            }
        }
        return _listStudents
    }

    fun createStudent(student: Student) {
        viewModelScope.launch {
            getApplication<Application>().applicationContext.bindService(
                intent,
                mConnection,
                AppCompatActivity.BIND_AUTO_CREATE
            )
            if (iaidlServerInterface != null) {
                iaidlServerInterface!!.createStudent(student)
            } else {
                Log.d(TAG, "not connection")
            }
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            getApplication<Application>().applicationContext.bindService(
                intent,
                mConnection,
                AppCompatActivity.BIND_AUTO_CREATE
            )
            if (iaidlServerInterface != null) {
                iaidlServerInterface!!.updateStudent(student)
            } else {
                Log.d(TAG, "not connection")
            }
        }
    }

    val bestStudents: LiveData<ArrayList<Student>>
        get() = findBest()

    fun findBest(): MutableLiveData<ArrayList<Student>> {
        val bestAverage = MutableLiveData<ArrayList<Student>>()
        viewModelScope.launch {
            getApplication<Application>().applicationContext.bindService(
                intent,
                mConnection,
                AppCompatActivity.BIND_AUTO_CREATE
            )
            if (iaidlServerInterface != null) {
                bestAverage.value = iaidlServerInterface!!.findBestAverage() as ArrayList<Student>
            } else {
                Log.d(TAG, "not connection")
            }
        }
        return bestAverage
    }

    val worstStudents: LiveData<ArrayList<Student>>
        get() = findWorst()

    fun findWorst(): MutableLiveData<ArrayList<Student>> {
        val worstAverage = MutableLiveData<ArrayList<Student>>()
        viewModelScope.launch {
            getApplication<Application>().applicationContext.bindService(
                intent,
                mConnection,
                AppCompatActivity.BIND_AUTO_CREATE
            )
            if (iaidlServerInterface != null) {
                worstAverage.value = iaidlServerInterface!!.findWorstAverage() as ArrayList<Student>
                Log.d(TAG, worstAverage.value.toString())
            } else {
                Log.d(TAG, "not connection")
            }
        }
        return worstAverage
    }

    val _searchStudent = MutableLiveData<Student?>()
    val searchStudent: LiveData<Student?>
        get() = _searchStudent
    var checkSearch = true

    fun searchStudentByName(name: String) {
        var student: Student?
        _searchStudent.value = null
        viewModelScope.launch {
            getApplication<Application>().applicationContext.bindService(
                intent,
                mConnection,
                AppCompatActivity.BIND_AUTO_CREATE
            )
            if (iaidlServerInterface != null) {
                student = iaidlServerInterface!!.getStudentByName(name) as Student?
                if (student != null) {
                    _searchStudent.value = student!!
                    checkSearch = true
                } else
                    checkSearch = false
                Log.d(TAG, "check: ${checkSearch.toString()}")
            } else {
                Log.d(TAG, "not connection")
            }
        }
    }
}