package com.example.day8assignmentteamanagment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.day8assignmentteamanagment.databinding.ItemTeaBinding
import com.example.day8assignmentteamanagment.db.Tea

class TeaDiff(val oldTea: List<Tea>, val newTea: List<Tea>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldTea.size
    override fun getNewListSize() = newTea.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTea[oldItemPosition].id == newTea[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTea === newTea
    }

}

class TeaAdapter() : RecyclerView.Adapter<TeaAdapter.ViewHolder>() {
    private val listTea: ArrayList<Tea> = arrayListOf()
    private lateinit var listener: OnItemClickListener
    fun submitData(list: List<Tea>) {
        val diff = DiffUtil.calculateDiff(TeaDiff(listTea, list))
        listTea.clear()
        listTea.addAll(list)
        diff.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClick(tea: Tea)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        listener = onItemClickListener
    }

    inner class ViewHolder(private var binding: ItemTeaBinding, val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(t: Tea) {
            binding.tea = t
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(listTea[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTeaBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listTea[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = listTea.size
}