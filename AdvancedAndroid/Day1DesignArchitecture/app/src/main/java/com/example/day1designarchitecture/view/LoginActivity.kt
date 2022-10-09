package com.example.day1designarchitecture.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.day1designarchitecture.BaseActivity
import com.example.day1designarchitecture.databinding.ActivityLoginBinding
import com.example.day1designarchitecture.model.User
import com.example.day1designarchitecture.presenter.LoginInterface
import com.example.day1designarchitecture.presenter.LoginPresenter

class LoginActivity : BaseActivity(), LoginInterface.View {
    lateinit var binding: ActivityLoginBinding
    private lateinit var loginPresenter: LoginPresenter
    private val TAG = "DHP"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginPresenter = LoginPresenter(this)
        loginPresenter.requestDataFromServer()

        binding.btnLogin.setOnClickListener() {
            loginPresenter.login(getUser(), this, binding.checkBox.isChecked)
        }
    }
    //đăng nhập thành công chuyển đến HomeActivity
    override fun loginSuccess(message: String) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
    //hiển thị dialog đăng nhập thất bại
    override fun loginError(message: String) {
        showDialog(message)
    }
    //lấy dữ liệu edittext
    fun getUser(): User {
        val email = binding.edtUsername.text.toString()
        val password = binding.edtPassword.text.toString()
        return User(email, password)
    }
    //tạo dialog
    fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login")
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}