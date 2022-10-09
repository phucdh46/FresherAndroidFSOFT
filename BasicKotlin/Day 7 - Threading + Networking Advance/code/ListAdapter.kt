package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(private val context: Context, private val arrayList: List<String>): BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return  p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(poition: Int, view: View?, parent: ViewGroup?): View {
        var convertView  = view
        convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        val text: TextView = convertView.findViewById(R.id.tv)
        text.text = arrayList[poition].toString()
        return convertView
    }
}