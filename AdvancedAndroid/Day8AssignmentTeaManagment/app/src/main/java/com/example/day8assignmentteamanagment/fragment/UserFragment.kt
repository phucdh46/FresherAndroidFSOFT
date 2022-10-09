package com.example.day8assignmentteamanagment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.day8assignmentteamanagment.databinding.FragmentUserBinding
import com.example.day8assignmentteamanagment.vm.UserViewModel

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.user.observe(viewLifecycleOwner) {
            binding.tvUser.text = "Hello: ${it.username.toString()}"
        }
    }


}