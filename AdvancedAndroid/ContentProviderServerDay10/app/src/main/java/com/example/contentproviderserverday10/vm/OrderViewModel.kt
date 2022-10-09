package com.example.contentproviderserverday10.vm

import android.app.Application
import android.content.ContentValues
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contentproviderserverday10.ServerContentProvider
import com.example.contentproviderserverday10.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(app: Application) : AndroidViewModel(app) {
    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order>
        get() = _order

    fun setOrder(order: Order) {
        _order.value = order
    }

    val list: LiveData<ArrayList<Order>>
        get() = getAllOrders()

    //get all orders
    private fun getAllOrders(): MutableLiveData<ArrayList<Order>> {
        val _list = MutableLiveData<ArrayList<Order>>()
        viewModelScope.launch(Dispatchers.IO) {
            var listOrder: ArrayList<Order> = arrayListOf()

            var cursor = getApplication<Application>().applicationContext.contentResolver.query(
                ServerContentProvider.CONTENT_URI,
                null, null, null, "timestamp"
            )
            if (cursor != null) {
                listOrder.clear()
                cursor!!.moveToFirst()
                while (!cursor!!.isAfterLast) {
                    listOrder.add(
                        Order(
                            cursor!!.getInt(0),
                            cursor!!.getString(1),
                            cursor!!.getString(2),
                            cursor!!.getString(3),
                        )
                    )
                    cursor!!.moveToNext()
                }
                withContext(Dispatchers.Main) {
                    _list.value = listOrder
                }
            }
        }
        return _list
    }

    //delete order
    fun deleteOrder(o: Order) {
        viewModelScope.launch {
            getApplication<Application>().contentResolver.delete(
                ServerContentProvider.CONTENT_URI,
                "id LIKE ?",
                arrayOf("${o.id}")
            )
        }
    }

    //add order
    fun addOrder(o: Order) {
        viewModelScope.launch {
            var values = ContentValues().apply {
                put("id", o.id)
                put("customername", o.customer_name)
                put("timestamp", o.timestamp)
                put("value", o.value)
            }
            getApplication<Application>().contentResolver.insert(
                ServerContentProvider.CONTENT_URI,
                values
            )
        }
    }

    //update order
    fun updateOrder(o: Order) {
        viewModelScope.launch {
            var values = ContentValues().apply {
                put("id", o.id)
                put("customername", o.customer_name)
                put("timestamp", o.timestamp)
                put("value", o.value)
            }
            getApplication<Application>().contentResolver.update(
                ServerContentProvider.CONTENT_URI, values,
                "id LIKE ?", arrayOf("${o.id}")
            )
        }
    }
}