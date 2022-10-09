package com.example.day12filterstudent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.day12filterstudent.adapter.StudentAdapter
import com.example.day12filterstudent.databinding.ActivityMainBinding
import com.example.day12filterstudent.model.Student
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class RxActivity : AppCompatActivity() {
    private val TAG = "DHP"
    private val listStudents: ArrayList<Student> = arrayListOf()
    private lateinit var binding: ActivityMainBinding
    private val adapter = StudentAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnFlow.setOnClickListener() {
            startActivity(Intent(this@RxActivity, FlowActivity::class.java))
        }
        //create recyclerview
        creatListStudent()
        //loadRecyclerview(listStudents)

        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)
        setSupportActionBar(binding.toolBar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        //search
        val item = menu.findItem(R.id.search)
        val searchView: SearchView = item.actionView as SearchView
        val p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        // Sử dụng object RxSearchObserve để search khi thay đổi hoặc nhập từ searchView
        RxSearchObserve.fromView(searchView)
            .map { text -> text.lowercase(Locale.ROOT).trim() }
            //check special characters
            .filter {
                !p.matcher(it).find()
                //it.trim().isNotEmpty()
            }
            //truyền dữ liệu search mới nhất với flapmap
            .flatMap { text -> Observable.just(searchData(text)).delay(100, TimeUnit.MILLISECONDS) }
            //Sử dụng distinctUntilChanged để tránh tạo các request trùng nhau
            .distinctUntilChanged()
            //Sử dụng debounce để người dùng chỉ search được trong 1 khoảng thời gian
            //Giảm số request đến server
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                Log.d("DHP1", text.toString())
                adapter.submitData(text as ArrayList<Student>)
            }
        return true
    }

    private fun searchData(text: String): List<Student> {
        return listStudents.filter {
            it.name.contains(text, true) || it.className.contains(
                text,
                true
            )
        }
    }


    //create 100 student
    private fun creatListStudent(): List<Student> {
        for (i in 1..100) {
            var gender: String? = null
            if (i % 2 == 0) gender = "male" else gender = "female"
            val idClass = i % 10
            listStudents.add(Student(i, "name $i", "$gender", "class $idClass"))
        }
        return listStudents
    }
}