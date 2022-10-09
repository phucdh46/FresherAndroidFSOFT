package com.example.assignmentday5

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener


class CardInforFragment : Fragment() {
    private lateinit var tvName: TextView
    lateinit var tvNumber: TextView
    lateinit var tvDate: TextView
    lateinit var btnUpdate: Button
    lateinit var btnClose: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_infor, container, false)
        tvName = view.findViewById(R.id.tvName)
        tvNumber = view.findViewById(R.id.tvNumberCard)
        tvDate = view.findViewById(R.id.tvExpiryDate)
        btnUpdate = view.findViewById(R.id.btnUpdate)
        btnClose = view.findViewById(R.id.btnClose)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get card from MainActivity
        val bundle = arguments
        val card = bundle!!.getSerializable("data") as Card
        //set data
        setData(card)
        // listen result from DialogFragment
        setFragmentResultListener("updatekey") { requestKey, bundle ->
            var cardUpdate = bundle.getSerializable("bundleUpdateKey") as Card
            Log.d("DHP", cardUpdate.toString())
            setData(cardUpdate)
        }

        // return MainActivity
        btnClose.setOnClickListener() {
            activity?.onBackPressed()
        }
        //Send card to DialogFragment and show DialogFragment
        btnUpdate.setOnClickListener() {
            setFragmentResult("requestKey", bundleOf("bundleKey" to card))
            var dialog = DialogFragment()
            dialog.show(parentFragmentManager, "customDialog")
        }
    }

    private fun setData(card: Card) {
        tvName.text = card.name
        tvNumber.text = card.cardNumber.toString()
        tvDate.text = card.expiryDate
    }
}