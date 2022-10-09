package com.example.contentproviderserverday10.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contentproviderserverday10.databinding.ItemOrderBinding
import com.example.contentproviderserverday10.model.Order
import java.time.Instant
import java.time.ZoneId

class OrderDiff(val oldOrder: List<Order>, val newOrder: List<Order>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldOrder.size
    override fun getNewListSize() = newOrder.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldOrder[oldItemPosition].id == newOrder[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldOrder === newOrder
    }

}

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private val orders: ArrayList<Order> = arrayListOf()
    private lateinit var listener: OnItemClickListener

    fun submitData(list: ArrayList<Order>) {
        val diff = DiffUtil.calculateDiff(OrderDiff(orders, list))
        orders.clear()
        orders.addAll(list)
        diff.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun OnItemClick(order: Order)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        listener = onItemClickListener
    }

    inner class ViewHolder(private var binding: ItemOrderBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(order: Order) {
            binding.order = order
            binding.tvTimestamp.text = convertTimestamp(order.timestamp.toString())
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener() {
                listener.OnItemClick(orders[adapterPosition])
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertTimestamp(timestamp: String): String {
        val dateTime = Instant.ofEpochSecond(timestamp.toLong()).atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        return "${dateTime.dayOfMonth}-${dateTime.monthValue}-${dateTime.year} ${dateTime.hour}:${dateTime.minute} "
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        orders[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = orders.size
}