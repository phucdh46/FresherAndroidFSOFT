package com.example.aidlclient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aidlclient.databinding.ItemStudentBinding
import com.example.aidlserver.Student

class StudentDiff(
    val oldStudent: List<Student>,
    val newStudent: List<Student>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldStudent.size

    override fun getNewListSize() = newStudent.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldStudent[oldItemPosition].id == newStudent[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldStudent === newStudent
    }

}

class StudentAdapter() : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    private val listStudents = arrayListOf<Student>()
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(student: Student);
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        listener = onItemClickListener
    }

    inner class ViewHolder(
        private val binding: ItemStudentBinding,
        onItemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener() {
                onItemClickListener.onItemClick(listStudents[adapterPosition])
            }
        }

        fun bind(s: Student) {
            binding.student = s
            binding.executePendingBindings()
        }
    }

    fun submitData(students: ArrayList<Student>) {
        val diff = DiffUtil.calculateDiff(StudentDiff(listStudents, students))
        listStudents.clear()
        listStudents.addAll(students)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemStudentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listStudents[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = listStudents.size
}