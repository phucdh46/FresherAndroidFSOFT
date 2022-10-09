package com.example.aidlclient.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aidlclient.R
import com.example.aidlclient.adapter.StudentAdapter
import com.example.aidlclient.databinding.FragmentHomeStudentBinding
import com.example.aidlclient.vm.StudentViewModel
import com.example.aidlserver.Student


class HomeStudentFragment : Fragment() {
    private val TAG: String = "DHP"
    private val viewModel: StudentViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeStudentBinding
    var students = arrayListOf<Student>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recyclerview
        val adapter = StudentAdapter()
        binding.rvStudent.adapter = adapter
        binding.rvStudent.layoutManager = LinearLayoutManager(context)
        adapter.setItemClickListener(object : StudentAdapter.OnItemClickListener {
            override fun onItemClick(student: Student) {
                Toast.makeText(context, " $student", Toast.LENGTH_SHORT).show()
                val b = Bundle()
                b.putParcelable("data", student)
                findNavController().navigate(
                    R.id.action_homeStudentFragment_to_editStudentFragment,
                    b
                )
            }
        })
        //get all students
        viewModel.listStudent.observe(viewLifecycleOwner) {
            Log.d(TAG, "main:${it.toString()}")
            adapter.submitData(it)
            students = it
        }
        //button get all students
        binding.btnGetAll.setOnClickListener() {
            viewModel.listStudent.observe(viewLifecycleOwner) { it ->
                adapter.submitData(it)
                students = it
            }
        }
        //find best average student
        binding.btnFindBest.setOnClickListener() {
            viewModel.bestStudents.observe(viewLifecycleOwner) {
                adapter.submitData(it)
            }
        }
        //find worst average student
        binding.btnFindWorst.setOnClickListener() {
            viewModel.worstStudents.observe(viewLifecycleOwner) {
                adapter.submitData(it)
            }
        }
        // button add
        binding.fltAdd.setOnClickListener() {
            val action =
                HomeStudentFragmentDirections.actionHomeStudentFragmentToCreateStudentFragment()
            findNavController().navigate(action)
        }
        //toobar search
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_search, menu)
                //search
                val item = menu.findItem(R.id.search)
                val searchView = item?.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        searchView.clearFocus()
                        //search
                        viewModel.searchStudentByName(query.toString())
                        //check search
                        if (!viewModel.checkSearch) {
                            val lists = arrayListOf<Student>()
                            adapter.submitData(lists)
                            showDialog("NOT FOUND")
                        }
                        //display search result in recyclerview
                        viewModel.searchStudent.observe(viewLifecycleOwner) { it ->
                            if (it != null) {
                                Log.d(TAG, it.toString())
                                val searchs = arrayListOf<Student>()
                                searchs.add(it)
                                adapter.submitData(searchs)
                            }
                        }
                        return false
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showDialog(s: String) {
        val dialog = AlertDialog.Builder(context).apply {
            setTitle("Search result")
            setMessage(s)
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }
        dialog.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "fragment onDestroy()")
        requireActivity().unbindService(viewModel.mConnection)
    }
}