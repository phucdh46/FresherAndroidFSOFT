package com.example.aidlclient.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.aidlclient.R
import com.example.aidlclient.databinding.FragmentCreateStudentBinding
import com.example.aidlclient.vm.StudentViewModel
import com.example.aidlserver.Student

class CreateStudentFragment : Fragment() {
    private val viewModel: StudentViewModel by activityViewModels()
    private lateinit var binding: FragmentCreateStudentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_save, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.save -> {
                        val student = Student(
                            null,
                            binding.edtName.text.toString(),
                            binding.edtAge.text.toString().toInt(),
                            binding.edtMathScore.text.toString().toInt(),
                            binding.edtPhysicScore.text.toString().toInt(),
                            binding.edtEnglishScore.text.toString().toInt()
                        )
                        viewModel.createStudent(student)
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}