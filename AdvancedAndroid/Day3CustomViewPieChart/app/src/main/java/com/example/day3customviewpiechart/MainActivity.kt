package com.example.day3customviewpiechart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.day3customviewpiechart.databinding.ActivityMainBinding
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //submit value array to pie chart
        binding.btnSubmit.setOnClickListener() {
            binding.pieChart.setShowText(true)
            binding.pieChart.setData(
                parseStringToList(
                    binding.edtInput.text.toString()
                )
            )
        }
    }

    // parse string with "," to list
    private fun parseStringToList(text: String): ArrayList<Int> {
        val delim = ","
        val valuesList = ArrayList<Int>()
        val stringValuesArray = Pattern.compile(delim).split(text)
        (stringValuesArray.indices).forEach {
            valuesList.add(stringValuesArray[it].toInt())
        }
        return valuesList
    }
}