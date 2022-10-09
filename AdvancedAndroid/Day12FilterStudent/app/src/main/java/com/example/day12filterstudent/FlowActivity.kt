package com.example.day12filterstudent

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.day12filterstudent.adapter.StudentAdapter
import com.example.day12filterstudent.databinding.ActivityFlowBinding
import com.example.day12filterstudent.model.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class FlowActivity : AppCompatActivity() {
    private val TAG: String = "DHP"
    private lateinit var binding: ActivityFlowBinding
    private val adapter = StudentAdapter()
    val flow = creatFlow()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRx.setOnClickListener() {
            startActivity(Intent(this@FlowActivity, RxActivity::class.java))
        }
        setSupportActionBar(binding.toolBar)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)


    }

    //create flow
    fun creatFlow(): Flow<Student> = flow {
        for (i in 1..100) {
            var gender: String? = null
            if (i % 2 == 0) gender = "male" else gender = "female"
            val idClass = i % 10
            emit(Student(i, "name $i", "$gender", "class $idClass"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        //search action
        val item = menu.findItem(R.id.search)
        val searchView: SearchView = item.actionView as SearchView
        val list = arrayListOf<Student>()
        CoroutineScope(Dispatchers.Main).launch {
            searchView.getQueryTextChangeStateFlow()
                //Sử dụng debounce để người dùng chỉ search được trong 1 khoảng thời gian
                //Giảm số request đến server
                .debounce(300)
                .filter { query ->
                    if (query.isEmpty()) {
                        list.clear()
                        return@filter false
                    } else {
                        list.clear()
                        return@filter true
                    }
                }
                //check special characters
                .filter {
                    !p.matcher(it).find()
                }
                //Sử dụng distinctUntilChanged để tránh tạo các request trùng nhau
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    dataFromNetwork(query)
                    /*.catch {
                        emitAll(flow)
                    }*/
                }
                //.flowOn(Dispatchers.Main)
                .collectLatest { result ->
                    list.add(result)
                    adapter.submitData(list)
                }
        }
        return true
    }

    private fun dataFromNetwork(query: String): Flow<Student> {

        return flow.filter { it.name.contains(query, true) || it.className.contains(query, true) }
    }

    fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
        val serachQuery = MutableStateFlow("")
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    serachQuery.value = newText
                }
                return true
            }
        })
        return serachQuery
    }
}