package com.example.day8assignmentteamanagment.fragment

import android.R
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.day8assignmentteamanagment.adapter.TeaAdapter
import com.example.day8assignmentteamanagment.databinding.FragmentHomeBinding
import com.example.day8assignmentteamanagment.db.Tea
import com.example.day8assignmentteamanagment.vm.TeaViewModel


class HomeFragment : Fragment() {
    private lateinit var listTeas: List<Tea>
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: TeaViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fbAdd.setOnClickListener() {
            val action = HomeFragmentDirections.actionHomeFragmentToTeaCreateFragment()
            findNavController().navigate(action)
        }
        //recyclerview
        val adapter = TeaAdapter()
        binding.rv.adapter = adapter
        viewModel.teas.observe(viewLifecycleOwner) {
            adapter.submitData(it)
            listTeas = it
        }
        binding.rv.layoutManager = LinearLayoutManager(context)

        adapter.setOnItemClickListener(object : TeaAdapter.OnItemClickListener {
            override fun onItemClick(tea: Tea) {
                //set tea to viewModel
                viewModel.setTea(tea)
                val action = HomeFragmentDirections.actionHomeFragmentToTeaEditFragment()
                findNavController().navigate(action)
                //send tea to TeaEditFragment with bundle
                /*val b = Bundle()
                b.putSerializable("data", tea)
                findNavController().navigate(
                    com.example.day8assignmentteamanagment.R.id.action_homeFragment_to_teaEditFragment,
                    b
                )*/
            }
        })

        //swipe action
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show()
                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.adapterPosition
                viewModel.deleteStudentById(listTeas[position].id!!)
            }

            private val background = ColorDrawable(resources.getColor(R.color.darker_gray))
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val itemView = viewHolder.itemView
                if (dX > 0) {
                    background.setBounds(
                        itemView.left,
                        itemView.top,
                        itemView.left + dX.toInt(),
                        itemView.bottom
                    )
                } else if (dX < 0) {
                    background.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                } else {
                    background.setBounds(0, 0, 0, 0)
                }
                background.draw(c)
            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rv)
    }
}