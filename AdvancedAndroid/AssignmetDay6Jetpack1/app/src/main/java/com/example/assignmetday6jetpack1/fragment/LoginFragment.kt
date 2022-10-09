package com.example.assignmetday6jetpack1.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.assignmetday6jetpack1.HomeActivity
import com.example.assignmetday6jetpack1.R
import com.example.assignmetday6jetpack1.databinding.FragmentLoginBinding
import com.example.assignmetday6jetpack1.vm.UserViewModel

class LoginFragment : Fragment() {
   private lateinit var binding: FragmentLoginBinding
    private val viewModel: UserViewModel by activityViewModels ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.btnSignUp.setOnClickListener() {
            requireActivity().supportFragmentManager.commit {
                replace<SignUpFragment>(R.id.fragment)
            }
        }
        //check login
        viewModel.checkLogin.observe(viewLifecycleOwner){
            if (it){
                startActivity(Intent(context,HomeActivity::class.java))
            }
        }
        viewModel.users.observe(viewLifecycleOwner){
            Log.d("DHP","login: ${it.toString()}")
        }
    }
}