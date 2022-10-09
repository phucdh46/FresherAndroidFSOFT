package com.example.assignmentday5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener


class DialogFragment : DialogFragment() {

    lateinit var card: Card
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSave = view.findViewById<Button>(R.id.btnSave)
        val edtName = view.findViewById<EditText>(R.id.edtName)
        val edtNumber = view.findViewById<EditText>(R.id.edtNumber)
        val edtDate = view.findViewById<EditText>(R.id.edtDate)
        //listen result from CardInforFragment
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            var card = bundle.getSerializable("bundleKey") as Card
            //set data
            edtName.setText(card.name.toString())
            edtNumber.setText(card.cardNumber.toString())
            edtDate.setText(card.expiryDate.toString())
        }
        //get card infor in dialog and send to CardInforFragment
        btnSave.setOnClickListener() {
            var cardUpdate = Card(
                edtName.text.toString(),
                Integer.parseInt(edtNumber.text.toString()),
                edtDate.text.toString()
            )
            setFragmentResult("updatekey", bundleOf("bundleUpdateKey" to cardUpdate))
            dismiss()
        }
    }
}