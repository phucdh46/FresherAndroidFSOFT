package com.example.day1designarchitecture.presenter

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import com.example.day1designarchitecture.model.User
import com.example.day1designarchitecture.network.UserService


class LoginPresenter : LoginInterface.Presenter, LoginInterface.Model.OnFinishedListener {
    private val TAG = "DHP"
    private var loginInterfaceView: LoginInterface.View
    private var loginInterfaceModel: LoginInterface.Model
    private var userApi: User? = null
    private lateinit var sharedpreferences: SharedPreferences
    private val SHARED_PREFS = "shared_prefs"

    constructor(loginInterface: LoginInterface.View) {
        this.loginInterfaceView = loginInterface
        loginInterfaceModel = UserService()
    }

    //lấy user từ API
    override fun requestDataFromServer() {
        loginInterfaceModel.getUser(this)
    }

    //kiểm tra login
    override fun login(user: User, context: Context, isChecked: Boolean) {
        if (!TextUtils.isEmpty(user.email) && Patterns.EMAIL_ADDRESS.matcher(user.email)
                .matches() &&
            !TextUtils.isEmpty(user.password) && user.password.length > 6
        ) {
            Log.d(TAG,user.toString())
            Log.d(TAG,userApi.toString())
            if (user.equals(userApi)) {
                sharedpreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                val editor = sharedpreferences.edit()
                if (isChecked) {
                    editor.putString("remember", "true")
                    editor.apply()
                } else {
                    editor.putString("remember", "false")
                    editor.apply()
                }
                loginInterfaceView.loginSuccess("login success")
            } else {
                loginInterfaceView.loginError("Your email or password is wrong!")
            }
        } else {
            loginInterfaceView.loginError("Password must at least 6 characters or Email invalid ")
        }
    }

    //kiểm tra checkBox remember
    override fun checkRemember(isChecked: String?) {
        if (isChecked.equals("true")) {
            loginInterfaceView.loginSuccess("success")
        }
    }

    //nhận về dữ liệu user từ API
    override fun onFinished(user: User) {
        userApi = user
    }

    //lỗi kết nối API, gửi đến view
    override fun onFailure(t: Throwable?) {
        loginInterfaceView.loginError(t.toString())
    }

}

