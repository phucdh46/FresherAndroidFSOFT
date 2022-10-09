package com.example.day5recycleviewnavigationui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.day5recycleviewnavigationui.model.Lesson

class LessonViewModel : ViewModel() {
    val _lesson = MutableLiveData<Lesson>()
    val lesson: LiveData<Lesson>
        get() = _lesson

    fun setLesson(lesson: Lesson) {
        _lesson.value = lesson
    }

    val _listLesson = MutableLiveData<ArrayList<Lesson>>()
    val listLesson: LiveData<ArrayList<Lesson>>
        get() = addLesson()

    private fun addLesson(): MutableLiveData<ArrayList<Lesson>> {
        val lessons = ArrayList<Lesson>()
        lessons.add(Lesson("T1-Basic Kotlin", "Kotlin", "20/6/2022", "60", true))
        lessons.add(Lesson("T2-Basic Android", "Android", "21/7/2022", "90", false))
        lessons.add(Lesson("T3-AdvancedAndroid", "Advance", "21/8/2022", "180", true))
        lessons.add(Lesson("T4-OJT", "OJT", "1/9/2022", "240", false))
        _listLesson.value = lessons
        return _listLesson
    }

}