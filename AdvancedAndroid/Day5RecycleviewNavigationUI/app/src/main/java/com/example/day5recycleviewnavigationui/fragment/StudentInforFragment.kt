package com.example.day5recycleviewnavigationui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.day5recycleviewnavigationui.R
import com.example.day5recycleviewnavigationui.adapter.LessonAdapter
import com.example.day5recycleviewnavigationui.databinding.FragmentStudentInforBinding
import com.example.day5recycleviewnavigationui.model.Lesson
import com.example.day5recycleviewnavigationui.vm.LessonViewModel

class StudentInforFragment : Fragment() {
    private val viewModel: LessonViewModel by activityViewModels()
    private lateinit var binding: FragmentStudentInforBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentInforBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setup recyclerview lesson
        val adapter = LessonAdapter()
        viewModel.listLesson.observe(viewLifecycleOwner, {
            adapter.submitData(it)
        })
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter
        adapter.setItemClickListener(object : LessonAdapter.OnItemClickListener {
            override fun onClick(lesson: Lesson) {
                viewModel.setLesson(lesson)
                requireActivity().supportFragmentManager.commit {
                    replace<LessonFragment>(R.id.fragment)
                }
            }
        })
    }
}