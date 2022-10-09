package com.example.day1designarchitecture

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.day1designarchitecture.presenter.LoginInterface
import com.example.day1designarchitecture.presenter.LoginPresenter

abstract class BaseActivity : AppCompatActivity(),LoginInterface.View {
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var sharedpreferences: SharedPreferences
    private var check: String? = null
    private val SHARED_PREFS = "shared_prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //get sharedpreferences
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        check = sharedpreferences.getString("remember", "")
        loginPresenter = LoginPresenter(this)
        // check remember login
        loginPresenter.checkRemember(check)
    }
}