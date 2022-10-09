package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFlowBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlowBinding
    val myFlow = flow<Int> {
        emit(1)
        delay(500)
        emit(2)
        delay(500)
        emit(3)
    }
    val flow1 = flowOf (
        11,22,33
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var adapter: ListAdapter? = null
        val lv: ListView = findViewById(R.id.lv)
        //take
        binding.btnTake.setOnClickListener(){
            lifecycleScope.launch {
                var arrayList: ArrayList<String> = ArrayList()
                myFlow.take(2).collect(){
                        value -> arrayList.add(value.toString())
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //transform
        binding.btnTransform.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch {
                myFlow.transform { value ->
                    emit(value*value)
                    emit(value*value*value)
                }.collect{
                        value -> arrayList.add(value.toString())
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //map
        binding.btnMap.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(){
                myFlow.map { it * it }.collect{ value ->
                     arrayList.add(value.toString())
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //filter
        binding.btnFilter.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(){
                myFlow.filter { it % 2 == 0 }.collect{
                        value -> arrayList.add(value.toString())
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //onEach
        binding.btnOnEach.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(){
                myFlow.onEach { delay(2000) }.collect{
                        value -> arrayList.add(value.toString())
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //reduce
        binding.btnReduce.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(){
                arrayList.add(myFlow.reduce { a, b -> a + b }.toString())
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //fold
        binding.btnFold.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(){
                arrayList.add(myFlow.fold(initial = 10){a, b -> a + b}.toString())
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //first
        binding.btnFirst.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(){
                arrayList.add(myFlow.first({ it % 2 == 0 }).toString())
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //zip
        binding.btnZip.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(Dispatchers.Main){
                myFlow.zip(flow1){ a, b -> "$a -> $b" }.collect(){
                         arrayList.add(it)
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
                }
            }
        //combine
        binding.btnCombine.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(Dispatchers.Main){
                myFlow.combine(flow1){ a, b -> "$a -> $b" }.collect(){
                    arrayList.add(it)
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        fun requestFlow(i: Int): Flow<String> = flow { // Đây là flowB
            emit("$i: First")
            delay(500) // wait 500 ms
            emit("$i: Second")
        }
        //flatMapConcat
        binding.btnFlagConcat.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(Dispatchers.Main){
                (1..3).asFlow().flatMapConcat { requestFlow(it) }.collect(){
                    value -> arrayList.add(value)
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //flatMapMerge
        binding.btnFlagMerge.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(Dispatchers.Main){
                (1..3).asFlow().flatMapMerge { requestFlow(it) }.collect(){
                        value -> arrayList.add(value)
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        //flatMapLatest
        binding.btnFlagLatest.setOnClickListener(){
            var arrayList: ArrayList<String> = ArrayList()
            lifecycleScope.launch(Dispatchers.Main){
                (1..3).asFlow().flatMapLatest { requestFlow(it) }.collect(){
                        value -> arrayList.add(value)
                }
                adapter= ListAdapter(applicationContext,arrayList)
                lv.adapter = adapter
            }
        }
        binding.btnRoom.setOnClickListener(){
            startActivity(Intent(this, RoomActivity::class.java))
        }
    }
}
