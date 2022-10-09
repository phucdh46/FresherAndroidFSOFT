package com.example.day8assignmentteamanagment.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.day8assignmentteamanagment.R
import com.example.day8assignmentteamanagment.databinding.FragmentTeaEditBinding
import com.example.day8assignmentteamanagment.db.Tea
import com.example.day8assignmentteamanagment.vm.TeaViewModel


class TeaEditFragment : Fragment() {
    private lateinit var binding: FragmentTeaEditBinding
    private val viewModel: TeaViewModel by activityViewModels()

    //private val args: TeaEditFragmentArgs by navArgs()
    private lateinit var tea: Tea
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeaEditBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get tea from HomeFragment by viewModel
        viewModel.tea.observe(viewLifecycleOwner) {
            Log.d("DHP1", it.toString())
            tea = it
            initView(it)
        }
        //get tea from HomeFragment by argument with bundle
        /*  val tea: Tea? = arguments?.getSerializable("data") as Tea?
          if (tea != null)
              initView(tea)*/

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menu.clear()
                menuInflater.inflate(R.menu.menu_save, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.save -> {
                        if (tea != null) {
                            val teaUpdate = Tea(
                                tea.id, binding.edtName.text.toString(),
                                binding.edtDescription.text.toString(),
                                binding.edtOrigin.text.toString(),
                                binding.edtIngredients.text.toString(),
                                binding.edtTime.text.toString().toInt(),
                                binding.edtCaffeine.text.toString().toInt()
                            )
                            viewModel.updateStudent(teaUpdate)
                        }
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initView(tea: Tea) {
        binding.edtName.setText(tea.name)
        binding.edtDescription.setText(tea.description)
        binding.edtOrigin.setText(tea.origin)
        binding.edtTime.setText(tea.sleepTime.toString())
        binding.edtIngredients.setText(tea.ingredients)
        binding.edtCaffeine.setText(tea.cafieneLevel.toString())
    }
}