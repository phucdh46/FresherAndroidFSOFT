package com.example.day8assignmentteamanagment.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.day8assignmentteamanagment.R
import com.example.day8assignmentteamanagment.databinding.FragmentTeaCreateBinding
import com.example.day8assignmentteamanagment.db.Tea
import com.example.day8assignmentteamanagment.vm.TeaViewModel


class TeaCreateFragment : Fragment() {
    private lateinit var binding: FragmentTeaCreateBinding
    private val viewModel: TeaViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeaCreateBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // create tea
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menu.clear()
                menuInflater.inflate(R.menu.menu_save, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.save -> {
                        val tea = Tea(
                            null, binding.edtName.text.toString(),
                            binding.edtDescription.text.toString(),
                            binding.edtOrigin.text.toString(),
                            binding.edtIngredients.text.toString(),
                            binding.edtTime.text.toString().toInt(),
                            binding.edtCaffeine.text.toString().toInt()
                        )
                        viewModel.insertStudent(tea)
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}