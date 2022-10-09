package com.example.day4dialogviewpapersettingscren.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.day4dialogviewpapersettingscren.R
import com.example.day4dialogviewpapersettingscren.databinding.FragmentDialogBinding

class DialogFragment : Fragment() {
    private lateinit var binding: FragmentDialogBinding
    private val TAG = "DHP"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvName.setOnClickListener {
            dialogName(object : OnSubmitBtnClick {
                override fun onClick(string: String) {
                    binding.tvName.text = string
                }
            })
        }
        binding.tvBthDay.setOnClickListener {
            dialogBthDay(object : OnSubmitBtnClick {
                override fun onClick(string: String) {
                    binding.tvBthDay.text = string
                }
            })
        }
        binding.tvSex.setOnClickListener {
            val arrSex = arrayOf("Male", "Female", "Other")
            dialogSingleChooseSex(arrSex, object : OnSubmitBtnClick {
                override fun onClick(string: String) {
                    binding.tvSex.text = string
                }
            })
        }
        binding.tvInterest.setOnClickListener {
            val arrInterest = arrayOf("Sport", "Music", "Game", "Travel")
            createDialogMultiChoose(arrInterest, object : OnSubmitBtnClick {
                override fun onClick(string: String) {
                    binding.tvInterest.text = string
                }
            })
        }
    }

    // create multi choose interest
    private fun createDialogMultiChoose(
        arrInterest: Array<String>,
        onSubmitBtnClick: DialogFragment.OnSubmitBtnClick
    ) {
        AlertDialog.Builder(context).apply {
            setTitle("Multi Choose Interest")
            var arrMultiChoose: ArrayList<String> = ArrayList()
            setMultiChoiceItems(arrInterest, null) { dialog, pos, isChecked ->
                if (isChecked) {
                    arrMultiChoose.add(arrInterest[pos])
                } else {
                    arrMultiChoose.remove(arrInterest[pos])
                }
            }
            setPositiveButton("OK") { dialog, _ ->
                onSubmitBtnClick.onClick(TextUtils.join(",", arrMultiChoose))
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        }.create().show()
    }

    // create dialog single choose sex
    private fun dialogSingleChooseSex(
        arrSex: Array<String>,
        onSubmitBtnClick: DialogFragment.OnSubmitBtnClick
    ) {
        AlertDialog.Builder(context).apply {
            setTitle("Single Choose Sex")
            var choose = ""
            setSingleChoiceItems(arrSex, 0) { dialog, pos ->
                choose = arrSex[pos]
            }
            setPositiveButton("OK") { dialog, _ ->
                onSubmitBtnClick.onClick(choose)
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }

    // dialog birthday
    private fun dialogBthDay(onSubmitBtnClick: OnSubmitBtnClick) {
        DatePickerDialog(requireContext()).apply {
            setOnDateSetListener { view, year, month, dayOfMonth ->
                val date = "$dayOfMonth/${month + 1}/$year"
                onSubmitBtnClick.onClick(date)
            }
        }.show()
    }

    // dialog edit name
    private fun dialogName(onSubmitBtnClick: OnSubmitBtnClick) {
        val dialog = AlertDialog.Builder(context)
        dialog.apply {
            setTitle("Edit name")
            setIcon(R.drawable.ic_launcher_background)
            val dialogView = layoutInflater.inflate(R.layout.dialog_edit_name, null)
            setView(dialogView)
            val edtName = dialogView.findViewById<EditText>(R.id.edtName)
            setPositiveButton("OK") { dialog, _ ->
                onSubmitBtnClick.onClick(edtName.text.toString())
                Log.d("DHP", edtName.text.toString())
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        }
        dialog.create().show()
    }

    interface OnSubmitBtnClick {
        fun onClick(string: String)
    }
}