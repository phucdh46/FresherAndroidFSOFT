package com.example.day5recycleviewnavigationui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.day5recycleviewnavigationui.databinding.FragmentLessonBinding
import com.example.day5recycleviewnavigationui.vm.LessonViewModel


class LessonFragment : Fragment() {
    private val viewModel: LessonViewModel by activityViewModels()
    private lateinit var binding: FragmentLessonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLessonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.lesson.observe(viewLifecycleOwner, {
            binding.tvTilte.setText(it.name)
        })
    }

}