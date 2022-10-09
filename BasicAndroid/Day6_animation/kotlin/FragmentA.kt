package com.example.day6animation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.day6animation.databinding.FragmentABinding

class FragmentA : Fragment() {
    private lateinit var binding: FragmentABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //create list view
        var list: ArrayList<String> = arrayListOf<String>("A", "B")
        val adapter = context?.let { MyAdapter(list, it) }
        binding.lv.adapter = adapter

        binding.lv.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                startActivity(Intent(context, DetailActivity::class.java))
            }
        }
        //add new song
        binding.btnAdd.setOnClickListener() {
            list.add(0, "Z")
            //lv.get(0).startAnimation(animUp)
            adapter?.notifyDataSetChanged()
        }
        //delete top song
        binding.btnDelete.setOnClickListener() {
            list.removeAt(0)
            adapter?.notifyDataSetChanged()
        }
    }
}