package com.example.assignmentday5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit

class CardInforActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_infor)
        val card = intent.getSerializableExtra("data")
        Log.d("DHP", card.toString())
        val mBundle = Bundle()
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = CardInforFragment()
        mBundle.putSerializable("data", card)
        mFragment.arguments = mBundle
        mFragmentTransaction.add(R.id.fragment, mFragment).commit()
    }
}