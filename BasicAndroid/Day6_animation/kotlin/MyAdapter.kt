package com.example.day6animation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.TextView

class MyAdapter(val list: ArrayList<String>, val context: Context) : BaseAdapter() {
    private var lastSize = 1
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any {
        return list[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        tvName.text = list[i]
        //set animation when add and delete
        val anim = AnimationUtils.loadAnimation(
            context,
            if (count > lastSize) R.anim.fade_translation_up else R.anim.fade_translation_down
        )

        if (i == 0) {
            view.startAnimation(anim)
        }
        lastSize = count
        return view
    }
}