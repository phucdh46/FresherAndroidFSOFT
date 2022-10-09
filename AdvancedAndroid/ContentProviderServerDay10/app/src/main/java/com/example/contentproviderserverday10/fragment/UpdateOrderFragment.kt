package com.example.contentproviderserverday10.fragment

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.contentproviderserverday10.R
import com.example.contentproviderserverday10.databinding.FragmentUpdateOrderBinding
import com.example.contentproviderserverday10.model.Order
import com.example.contentproviderserverday10.vm.OrderViewModel
import java.time.Instant
import java.time.ZoneId
import java.util.*


class UpdateOrderFragment : Fragment() {
    private val TAG: String = "DHP"
    private val viewModel: OrderViewModel by activityViewModels()
    private lateinit var order: Order
    private lateinit var binding: FragmentUpdateOrderBinding
    private var timestamp: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //open date time dialog
        binding.tvDate.setOnClickListener {
            createDialogDateTimePicker(order.timestamp)
        }
        //user viewModel order
        viewModel.order.observe(viewLifecycleOwner) {
            order = it
            timestamp = it.timestamp
            binding.edtName.setText(it.customer_name)
            binding.tvDate.text = convertTimestamp(it.timestamp)
            binding.edtValue.setText(it.value)
        }
        // Or use bundle load all orders
        /* val order: Order? = arguments?.getSerializable("data") as Order?
         if (order != null) {
             binding.edtName.setText(order.customer_name)
             binding.tvTimestamp.setText(order.timestamp)
             binding.edtValue.setText(order.value)
         }*/

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_save, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.save -> {
                        if (timestamp != null) {
                            //update order
                            val orderUpdate = Order(
                                order.id, binding.edtName.text.toString(),
                                timestamp!!, binding.edtValue.text.toString()
                            )
                            viewModel.updateOrder(orderUpdate)
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(context, "Please choose date, time", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createDialogDateTimePicker(t: String) {
        val dialogView = layoutInflater.inflate(R.layout.date_time_picker, null)
        val datePicker = dialogView.findViewById<DatePicker>(R.id.date_picker)
        val timePicker = dialogView.findViewById<TimePicker>(R.id.time_picker)
        val alertDialog = AlertDialog.Builder(context).create()
        //set date time picker with present value
        val dateTime = Instant.ofEpochSecond(t.toLong()).atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        datePicker.init(dateTime.year, dateTime.monthValue - 1, dateTime.dayOfMonth, null)
        timePicker.hour = dateTime.hour
        timePicker.minute = dateTime.minute
        //get date time
        alertDialog.setView(dialogView)
        var time: Long? = null
        dialogView.findViewById<Button>(R.id.btnSet)
            .setOnClickListener {
                val calendar = GregorianCalendar(
                    datePicker.year,
                    datePicker.month, datePicker.dayOfMonth, timePicker.hour, timePicker.minute
                )
                binding.tvDate.text =
                    "${datePicker.dayOfMonth}-${datePicker.month}-${datePicker.year} ${timePicker.hour}:${timePicker.minute}"
                time = calendar.timeInMillis / 1000L
                Log.d(TAG, time.toString())
                alertDialog.dismiss()
                timestamp = time.toString()
            }
        alertDialog.show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertTimestamp(timestamp: String): String {
        val dateTime = Instant.ofEpochSecond(timestamp.toLong()).atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        return "${dateTime.dayOfMonth}-${dateTime.monthValue}-${dateTime.year} ${dateTime.hour}:${dateTime.minute} "
    }

}