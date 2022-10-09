package com.example.day5recycleviewnavigationui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.day5recycleviewnavigationui.databinding.ItemLessonBinding
import com.example.day5recycleviewnavigationui.databinding.ItemLessonCompletedBinding
import com.example.day5recycleviewnavigationui.model.Lesson

class LessonAdapter() :
    RecyclerView.Adapter<LessonAdapter.ViewHolder>() {
    private val lessonList: ArrayList<Lesson> = arrayListOf()
    private lateinit var binding1: ItemLessonCompletedBinding
    private lateinit var binding2: ItemLessonBinding
    private lateinit var listener: OnItemClickListener
    fun submitData(list: ArrayList<Lesson>) {
        lessonList.clear()
        lessonList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(lesson: Lesson)
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener() {
                onItemClickListener.onClick(lessonList[adapterPosition])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (lessonList[position].isCompleted)
            return 1
        else
            return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 1) {
            binding1 = ItemLessonCompletedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(binding1.root, listener)
        } else {
            binding2 = ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding2.root, listener)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = lessonList[position]
        if (holder.itemViewType == 1) {
            binding1.tvTitle.text = lesson.title
            binding1.tvName.text = lesson.name
            binding1.tvDate.text = lesson.date
            binding1.tvTime.text = "${lesson.time} minutes"
            binding1.tvDayLeft.text = "Completed"
        } else {
            binding2.tvTitle.text = lesson.title
            binding2.tvName.text = lesson.name
            binding2.tvDate.text = lesson.date
            binding2.tvTime.text = "${lesson.time} minutes"
            binding2.tvDayLeft.text = "10 day left!"
        }
    }

    override fun getItemCount() = lessonList.size
}