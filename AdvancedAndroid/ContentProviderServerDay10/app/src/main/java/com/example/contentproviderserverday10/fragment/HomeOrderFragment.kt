package com.example.contentproviderserverday10.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contentproviderserverday10.R
import com.example.contentproviderserverday10.adapter.OrderAdapter
import com.example.contentproviderserverday10.databinding.FragmentHomeBinding
import com.example.contentproviderserverday10.model.Order
import com.example.contentproviderserverday10.vm.OrderViewModel


class HomeOrderFragment : Fragment() {
    private val viewModel: OrderViewModel by activityViewModels()
    private val TAG: String = "DHP"
    private lateinit var binding: FragmentHomeBinding
    private var orders: ArrayList<Order> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = OrderAdapter()
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(context)
        adapter.setItemOnClickListener(object : OrderAdapter.OnItemClickListener {
            override fun OnItemClick(order: Order) {
                //use bundle or viewModel send order to UpdateFragment
                //val b = Bundle()
                //b.putSerializable("data", order)
                viewModel.setOrder(order)
                findNavController().navigate(
                    R.id.action_homeFragment_to_updateOrderFragment
                    //,b
                )
            }
        })
        //viewModel load all orders to recyclerview
        viewModel.list.observe(viewLifecycleOwner) { it ->
            adapter.submitData(it)
            orders = it
            Log.d(TAG, "list: ${it.toString()}")
        }

        // swipe to delete order
        val itemTouchCallBack: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val orderDelete = orders[viewHolder.adapterPosition]
                    //deleteOrder(listOrder[viewHolder.adapterPosition])
                    viewModel.deleteOrder(orderDelete)
                    viewModel.list.observe(viewLifecycleOwner) { it ->
                        adapter.submitData(it)
                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rv)
        binding.flbAdd.setOnClickListener() {
            val action = HomeOrderFragmentDirections.actionHomeFragmentToAddOrderFragment()
            findNavController().navigate(action)
        }
    }
}