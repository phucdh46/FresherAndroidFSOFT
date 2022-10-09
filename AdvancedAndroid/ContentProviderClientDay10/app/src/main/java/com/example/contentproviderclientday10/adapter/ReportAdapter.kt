package com.example.contentproviderclientday10.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contentproviderclientday10.databinding.ItemReportBinding
import com.example.contentproviderclientday10.model.Report

class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {
    private val reports: ArrayList<Report> = arrayListOf()

    class ViewHolder(private var binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(report: Report) {
            binding.report = report
            binding.executePendingBindings()
        }
    }

    fun submitData(listReport: ArrayList<Report>) {
        reports.clear()
        reports.addAll(listReport)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        reports[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = reports.size
}