package com.example.contentproviderserverday10.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.contentproviderserverday10.R
import com.example.contentproviderserverday10.databinding.FragmentAddOrderBinding
import com.example.contentproviderserverday10.model.Order
import com.example.contentproviderserverday10.vm.OrderViewModel
import java.util.*


class AddOrderFragment : Fragment() {
    private val TAG: String = "DHP"
    private val viewModel: OrderViewModel by activityViewModels()
    private var timestamp: String? = null
    private lateinit var binding: FragmentAddOrderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //open dialog date time
        binding.tvTimestamp.setOnClickListener {
            createDialogDateTimePicker()
        }
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_save, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.save -> {
                        if (timestamp != null) {
                            //add order
                            val orderAdd = Order(
                                null, binding.edtName.text.toString(),
                                timestamp!!, binding.edtValue.text.toString()
                            )
                            viewModel.addOrder(orderAdd)
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(context, "Please choose date,time", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun createDialogDateTimePicker() {
        val dialogView = layoutInflater.inflate(R.layout.date_time_picker, null)
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setView(dialogView)
        dialogView.findViewById<Button>(R.id.btnSet)
            .setOnClickListener {
                val datePicker = dialogView.findViewById<DatePicker>(R.id.date_picker)
                val timePicker = dialogView.findViewById<TimePicker>(R.id.time_picker)
                val calendar = GregorianCalendar(
                    datePicker.year,
                    datePicker.month, datePicker.dayOfMonth, timePicker.hour, timePicker.minute
                )
                val time: Long = calendar.timeInMillis / 1000L
                Log.d(TAG, time.toString())
                timestamp = time.toString()
                binding.tvTimestamp.text =
                    "${datePicker.dayOfMonth}-${datePicker.month}-${datePicker.year} ${timePicker.hour}:${timePicker.minute}"
                alertDialog.dismiss()
            }
        alertDialog.show()
    }
}