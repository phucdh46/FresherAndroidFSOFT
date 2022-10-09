package com.example.day8assignmentteamanagment.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.day8assignmentteamanagment.db.Tea
import com.example.day8assignmentteamanagment.db.TeaDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeaViewModel @Inject constructor(val teaDao: TeaDao) : ViewModel() {

    //class TeaViewModel(app: Application) : AndroidViewModel(app) {
    //   private val teaDao = TeaDatabase.getInstance(app.applicationContext).teaDao()
    var _tea = MutableLiveData<Tea>()
    val tea: LiveData<Tea>
        get() = _tea

    fun setTea(t: Tea) {
        _tea.value = t
    }

    val teas = teaDao.getAllTeas()
    fun insertStudent(s: Tea) {
        viewModelScope.launch(Dispatchers.IO) {
            teaDao.insertTea(s)
        }
    }

    fun updateStudent(s: Tea) {
        viewModelScope.launch(Dispatchers.IO) {
            teaDao.updateTea(s)
        }
    }

    fun deleteStudentById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            teaDao.deleteTea(id)
        }
    }
}