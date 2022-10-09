package com.example.contentproviderclientday10.vm

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contentproviderclientday10.model.Order
import com.example.contentproviderclientday10.model.Report
import kotlinx.coroutines.launch
import java.util.*

class ReportViewModel(app: Application) :
    AndroidViewModel(app) {

    val order: LiveData<ArrayList<Order>>
        get() = getOrders()

    //get all orders from server
    fun getOrders(): MutableLiveData<ArrayList<Order>> {
        var _orders = MutableLiveData<ArrayList<Order>>()
        var listOrder = arrayListOf<Order>()
        viewModelScope.launch {
            var uri = Uri.parse("content://com.dhp.server.ServerContentProvider/orders")
            val cursor = getApplication<Application>().applicationContext.contentResolver
                .query(uri, null, null, null, "timestamp")
            if (cursor != null) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    listOrder.add(
                        Order(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                        )
                    )
                    cursor.moveToNext()
                }
            }
            _orders.value = listOrder
        }
        return _orders
    }

    var _reports = MutableLiveData<ArrayList<Report>>()
    val report: LiveData<ArrayList<Report>>
        get() = getReports()

    //convert list order to list report
    fun getReports(): MutableLiveData<ArrayList<Report>> {
        //get list orders
        val listOrder = getOrders().value
        Log.d("DHP", listOrder.toString())
        //convert timestamp to date
        listOrder?.forEach { it ->
            it.timestamp = convertTimestampToDate(it.timestamp)
        }
        //create list report from list order with date, totalValue, vipCustomer
        val listReport: ArrayList<Report> = arrayListOf()
        var date = listOrder!!.get(0).timestamp
        var totalValue = listOrder!![0].value.toString().toInt()
        var mostValuable = listOrder[0].value.toString().toInt()
        var vipCustomer = listOrder[0].customer_name.toString()

        for (i in 1 until listOrder.size) {
            //group by timestamp
            if (date.equals(listOrder[i].timestamp)) {
                //total value
                totalValue += listOrder[i].value.toString().toInt()
                //find most valuable customer
                if (mostValuable < listOrder[i].value.toString().toInt()) {
                    mostValuable = listOrder[i].value.toString().toInt()
                    vipCustomer = listOrder[i].customer_name.toString()
                }
                if (i == listOrder.size - 1) {
                    listReport.add(Report(date, totalValue, vipCustomer))
                }
            } else {
                //add to list report when timestamp not match
                listReport.add(Report(date, totalValue, vipCustomer))
                //set data = order[i]
                date = listOrder[i].timestamp
                totalValue = listOrder[i].value.toString().toInt()
                mostValuable = listOrder[i].value.toString().toInt()
                vipCustomer = listOrder[i].customer_name.toString()
                if (i == listOrder.size - 1) {
                    listReport.add(Report(date, totalValue, vipCustomer))
                }
            }
        }
        Log.d("DHP", listReport.toString())
        _reports.value = listReport
        return _reports
    }

    //convert timestamp to date(dd-MM-yyyy)
    private fun convertTimestampToDate(timestamp: String): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp.toLong() * 1000L
        val date = android.text.format.DateFormat.format("dd-MM-yyyy", calendar)
        return date.toString()
    }
}